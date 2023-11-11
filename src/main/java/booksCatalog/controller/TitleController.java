package booksCatalog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Publisher;
import booksCatalog.entities.Title;
import booksCatalog.repo.AuthorRepo;
import booksCatalog.repo.PublisherRepo;
import booksCatalog.repo.TitleDetailsDTO;
import booksCatalog.repo.TitleRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class TitleController {
	@Autowired
	TitleRepo titleRepo;

	@Autowired
	PublisherRepo publisherRepo;

	@Autowired
	AuthorRepo authorRepo;

	// listAllTitles
	@CrossOrigin
	@GetMapping("/titles")
	@Operation(summary = "Get all Titles", description = "Retrieve a list of all titles from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of all titles"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Title> getAllTitles() {
		return titleRepo.findAll();
	}

	// add title
	@PostMapping("/titles")
	@Operation(summary = "Add title", description = "Add a new title to the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Title added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void addTitle(@Valid @RequestBody Title title) {
		var optTitle = titleRepo.findById(title.getTitleId());
		try {
			if (optTitle.isEmpty()) {
				titleRepo.save(title);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title Already Existed");
			}
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// update title
	@PutMapping("/titles/{titleId}")
	@Operation(summary = "Update title by titleId", description = "Update the title of a book by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Title updated successfully"),
			@ApiResponse(responseCode = "404", description = "Title ID not found") })
	public void updateTitle(@PathVariable("titleId") String id, @RequestParam("title") String newtitle) {
		var optTitle = titleRepo.findById(id);
		if (optTitle.isPresent()) {
			var title = optTitle.get();
			title.setTitle(newtitle);
			titleRepo.save(title);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Title ID Not Found!");
		}
	}

	// delete title
	@DeleteMapping("/titles/{titleId}")
	@Operation(summary = "Delete title by titleId", description = "Delete a title by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Title deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Title ID not found") })
	public void deleteOneTitle(@PathVariable("titleId") String id) {
		var optTitle = titleRepo.findById(id);
		if (optTitle.isPresent()) {
			titleRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Title ID Not Found!");
		}
	}

	// 7 list All titles By Publisher
	@GetMapping("/titles/publishers/{id}")
	@Operation(summary = "Get titles by publisher", description = "Retrieve a list of titles associated with the specified publisher")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Titles found successfully"),
			@ApiResponse(responseCode = "404", description = "Publisher not found or no titles found in the specified publisher") })
	public List<Title> getTitlesByPublisher(@PathVariable("id") String pubId) {
		Optional<Publisher> publisher = publisherRepo.findById(pubId);

		if (publisher == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found");
		}

		List<Title> titles = titleRepo.findByPublisher(publisher);

		if (titles.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No titles found in the specified publisher");
		}

		return titles;
	}

	// 8 list all titles by author
	@GetMapping("/titleAndAuthors")
	@Operation(summary = "Get all titles by author", description = "Retrieve a list of titles along with their associated authors")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Titles and authors found successfully"),
			@ApiResponse(responseCode = "404", description = "No titles found with associated authors") })
	public List<Object[]> displayTitleAndAuthors() {
		var listObj = titleRepo.findTitleAndAuthor();

		if (listObj.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No titles found with associated authors");
		}

		return listObj;
	}

	// 9 List all titles that match the given title
	@GetMapping("/titles/title")
	@Operation(summary = "Get all titles that match the given title", description = "Retrieve a list of titles that contain a specified string in their title")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Titles found successfully"),
			@ApiResponse(responseCode = "404", description = "No titles found matching the given string") })
	public List<Title> getTitleByGivenTitle(@RequestParam("title") String title) {
		List<Title> titles = titleRepo.findByTitleContaining(title);

		if (titles.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Titles Matching Given String");
		}

		return titles;
	}

	// 10 list all titles by the range of price
	@GetMapping("/titlesbyprice")
	@Operation(summary = "Get all titles by the range of price", description = "Retrieve a list of titles within a specified price range")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Titles found successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request (e.g., invalid price range)") })
	public List<Title> getTitlesByPriceRange(@RequestParam("minPrice") Double minPrice,
			@RequestParam("maxPrice") Double maxPrice) {
		if (minPrice >= maxPrice) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid price range");
		}
		return titleRepo.findByPriceBetween(minPrice, maxPrice);
	}

	// 11 list top 5 titles by year to day sale
	@GetMapping("/titles/top5byytdsales")
	@Operation(summary = "Get top 5 titles by year to day sale", description = "Retrieve a list of top 5 titles based on year-to-date sales")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Titles found successfully"),
			@ApiResponse(responseCode = "404", description = "No titles found") })
	public List<Title> getTop5TitlesByYtdSales() {
		List<Title> top5Titles = titleRepo.findTop5ByOrderByYtdsalesDesc();

		if (top5Titles.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No titles found.");
		}

		return top5Titles;
	}

	// 13 List details of a title
	@GetMapping("/allDetailsByTitle")
	@Operation(summary = "Get details of a title", description = "Retrieve details of a title based on the provided titleId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Title details found successfully"),
			@ApiResponse(responseCode = "404", description = "Title details not found for the provided titleId") })
	public List<TitleDetailsDTO> displayTitleDetails(@RequestParam("titleId") String titleId) {
		var title = titleRepo.findAllDetailsByTitle(titleId);

		if (title.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Title details not found for the provided titleId");
		}

		return title;
	}

}
