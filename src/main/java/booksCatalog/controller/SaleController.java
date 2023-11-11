package booksCatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import booksCatalog.entities.Sale;
import booksCatalog.repo.SaleByTitleDTO;
import booksCatalog.repo.SaleRepo;
import booksCatalog.repo.StoreSumOfQtyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class SaleController {
	@Autowired
	SaleRepo saleRepo;

	// 6 List all sales
	@GetMapping("/sales")
	@Operation(summary = "Get all sales", description = "Retrieve a list of all sales from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of all sales"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Sale> getAllSales() {
		return saleRepo.findAll();
	}

	// add sale
	@PostMapping("/addSale")
	@Operation(summary = "Add sale", description = "Add a new sale to the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sale added successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request") })
	public void addSale(@Valid @RequestBody Sale newSale) {
		saleRepo.save(newSale);
	}

	// update sale
	@PutMapping("/updateSale")
	@Operation(summary = "Update sale", description = "Update an existing sale")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sale updated successfully"),
			@ApiResponse(responseCode = "404", description = "Sale not found") })
	public void updateSale(@Valid @RequestBody Sale updatedSale) {
		var opSale = saleRepo.findById(updatedSale.getPk());
		if (opSale.isPresent()) {
			var sale = opSale.get();
			sale.setSold(updatedSale.getSold());
			saleRepo.save(sale);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale Not Found!");
		}
	}

	// delete sale
	@DeleteMapping("/deleteSale")
	@Operation(summary = "Delete sale", description = "Delete an existing sale")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sale deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Sale not found") })
	public void deleteSale(@Valid @RequestBody Sale deleteSale) {
		saleRepo.deleteById(deleteSale.getPk());
	}

	// 12 list top 5 stores by total titles sold
	@GetMapping("/top5stores")
	@Operation(summary = "Get top 5 stores", description = "Retrieve the top 5 stores by total titles sold")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved top 5 stores"),
			@ApiResponse(responseCode = "404", description = "No stores found") })
	public List<StoreSumOfQtyDTO> getTop5Stores() {
		var listStores = saleRepo.findByTitleSold(PageRequest.of(0, 5));
		return listStores;
	}

	// 15 List sales across the stores for a given title
	@GetMapping("/salesByTitle")
	@Operation(summary = "Get sales by title", description = "Retrieve sales information for a specific title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved sales information for the title"),
			@ApiResponse(responseCode = "404", description = "No sales found for the title") })
	public List<SaleByTitleDTO> getSalesByTitle(@RequestParam("titleId") String titleId) {
		var listSales = saleRepo.findSaleByTitle(titleId);
		if (listSales.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale Not Found!");
		} else
			return listSales;

	}

}
