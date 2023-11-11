package booksCatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import booksCatalog.entities.Publisher;
import booksCatalog.repo.PublisherRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class PublisherController {
	@Autowired
	PublisherRepo publisherRepo;

	@GetMapping("/publishers")
	@Operation(summary = "Get all publishers", description = "Retrieve a list of all publishers from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of all publishers"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Publisher> getAllPublishers() {
		return publisherRepo.findAll();
	}

	// add Publisher
	@PostMapping("/addpublishers")
	@Operation(summary = "Add publisher", description = "Add a new publisher to the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publisher added successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void addPublisher(@Valid @RequestBody Publisher publisher) {
		publisherRepo.save(publisher);
	}

	// update publisher
	@PutMapping("/publishers/{pubId}")
	@Operation(summary = "update publisher by pubId", description = "Update the name of a publisher by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publisher updated successfully"),
			@ApiResponse(responseCode = "404", description = "Publisher ID not found") })
	public void updatePublisher(@PathVariable("pubId") String id, @RequestParam("pubName") String newpubName) {
		var optPublisher = publisherRepo.findById(id);
		if (optPublisher.isPresent()) {
			var publisher = optPublisher.get();
			publisher.setPubName(newpubName);
			publisherRepo.save(publisher);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher ID Not Found!");
		}
	}

	// delete publisher
	@DeleteMapping("/publishers/{pubId}")
	@Operation(summary = "Delete publisher by pubId", description = "Delete a publisher by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publisher deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Publisher ID not found") })
	public void deleteOnePublisher(@PathVariable("pubId") String id) {
		var optPublisher = publisherRepo.findById(id);
		if (optPublisher.isPresent()) {
			publisherRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher ID Not Found!");
		}
	}

}
