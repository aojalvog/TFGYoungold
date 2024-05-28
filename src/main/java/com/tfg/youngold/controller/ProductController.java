package com.tfg.youngold.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.youngold.model.Product;
import com.tfg.youngold.response.ProductResponseRest;
import com.tfg.youngold.services.IProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/youngold")
public class ProductController {

	private IProductService productService;

	public ProductController(IProductService productService) {
		this.productService = productService;
	}

	// GET
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> searchById(@PathVariable Long id) {
		log.info("<---Entrando en el método searchById--->");
		return productService.searchById(id);
	}

	@GetMapping("/products/filter/{name}")
	public ResponseEntity<ProductResponseRest> searchByName(@PathVariable String name) {
		log.info("<---Entrando en el método searchByName--->");
		return productService.searchByName(name);
	}

	@GetMapping("/products")
	public ResponseEntity<ProductResponseRest> search() {
		log.info("<---Entrando en el método search--->");
		return productService.search();
	}

	// POST

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ProductResponseRest> save(@RequestParam("name") String name,
			@RequestParam("price") Double price, @RequestParam("account") int account,
			@RequestParam("categoryId") Long categoryID) {
		Product product = new Product();
		product.setName(name);
		product.setAccount(account);
		product.setPrice(price);
		log.info("<---Entrando en el método save--->");
		return productService.save(product, categoryID);
	}

	// PUT

	@PutMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> update(@RequestParam("name") String name,
			@RequestParam("price") Double price, @RequestParam("account") int account,
			@RequestParam("categoryId") Long categoryID, @PathVariable Long id) {
		Product product = new Product();
		product.setName(name);
		product.setAccount(account);
		product.setPrice(price);
		log.info("<---Entrando en el método update--->");
		return productService.update(product, categoryID, id);

	}

	// DELETE

	@DeleteMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> delete(@PathVariable Long id) {
		log.info("<---Entrando en el método delete--->");
		return productService.delete(id);
	}

}
