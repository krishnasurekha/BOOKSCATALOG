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

import booksCatalog.entities.Store;
import booksCatalog.entities.Title;
import booksCatalog.repo.StoreRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class StoreController {
	@Autowired
	StoreRepo storeRepo;

	@GetMapping("/stores")
	@Operation(summary = "Get all stores", description = "Retrieve a list of all stores from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of all stores"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Store> getAllStores() {
		return storeRepo.findAll();
	}

	// add store
	@PostMapping("/stores")
	@Operation(summary = "Add store", description = "Add a new store to the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Store added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void addStore(@Valid @RequestBody Store store) {
		var optStore = storeRepo.findById(store.getStoreId());
		try {
			if (optStore.isEmpty()) {
				storeRepo.save(store);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store Already Existed");
			}
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// update store
	@PutMapping("/stores/{storeId}")
	@Operation(summary = "Update Store by storeid", description = "Update the location of a store by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Store updated successfully"),
			@ApiResponse(responseCode = "404", description = "Store ID not found") })
	public void updateStore(@PathVariable("storeId") String id, @RequestParam("location") String newlocation) {
		var optStore = storeRepo.findById(id);
		if (optStore.isPresent()) {
			var store = optStore.get();
			store.setLocation(newlocation);
			storeRepo.save(store);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store ID Not Found!");
		}
	}

	// delete title
	@DeleteMapping("/stores/{storeId}")
	@Operation(summary = "Delete store by storeId", description = "Delete a store by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Store deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Store ID not found") })
	public void deleteOneStore(@PathVariable("storeId") String id) {
		var optStore = storeRepo.findById(id);
		if (optStore.isPresent()) {
			storeRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store ID Not Found!");
		}
	}

	// 14 Take store id and list all titles sold there
	@GetMapping("findByStoreId/{storeId}")
	@Operation(summary = "Get Titles By store_id", description = "Get All Titles By Given store_id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved ALl Titles Successful"),
			@ApiResponse(responseCode = "404", description = "store_id Not Found"),
			@ApiResponse(responseCode = "500", description = "An issue occurred on the server while processing a request") })
	public List<Title> findbyStoreId(@PathVariable("storeId") String storeId) {
		var list = storeRepo.findByStoreId(storeId);
		return list;

	}

}
