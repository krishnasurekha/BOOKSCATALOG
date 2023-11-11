package booksCatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Author;
import booksCatalog.repo.AuthorRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class AuthorController {
	@Autowired
	private AuthorRepo authorRepo;

	@GetMapping("/authors")
	@Operation(summary = "Get all authors", description = "Retrieves a list of all authors from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of all authors"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Author> getAllAuthors() {
		return authorRepo.findAll();
	}

	// add author
	@PostMapping("/authors")
	@Operation(summary = "Add author", description = "Add a new author to the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Author added successfully"),
			@ApiResponse(responseCode = "400", description = "Author already exists") })
	public Author addAuthor(@Parameter(description = "Author object to be added") @Valid @RequestBody Author author) {
		try {
			var optAuthor = authorRepo.findById(author.getAuId());

			if (optAuthor.isPresent())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Existed");
			authorRepo.save(author);
			return author;

		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// update author
	@PutMapping("/authors/{auId}")
	@Operation(summary = "Update author", description = "Update author's name based on the provided ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Author updated successfully"),
			@ApiResponse(responseCode = "404", description = "Author ID not found") })
	public void updateAuthor(@PathVariable("auId") String id, @RequestParam("auName") String newauName) {
		var optAuthor = authorRepo.findById(id);
		if (optAuthor.isPresent()) {
			var author = optAuthor.get();
			author.setAuName(newauName);
			authorRepo.save(author);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author ID Not Found!");
		}
	}

	// delete author
	@DeleteMapping("/authors/{auId}")
	@Operation(summary = "Delete author", description = "Delete an author based on the provided ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Author deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Author ID not found") })
	public void deleteOneAuthor(@PathVariable("auId") String id) {
		var optAuthor = authorRepo.findById(id);
		if (optAuthor.isPresent()) {
			authorRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author ID Not Found!");
		}
	}
}
