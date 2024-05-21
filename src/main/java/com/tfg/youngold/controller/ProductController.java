package com.tfg.youngold.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.youngold.model.Product;
import com.tfg.youngold.response.ProductResponseRest;
import com.tfg.youngold.services.IProductService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/youngold")
public class ProductController {

	private IProductService productService;

	public ProductController(IProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/products")
	public ResponseEntity<ProductResponseRest> save(@RequestParam("name") String name,
			@RequestParam("price") Double price, @RequestParam("account") int account,
			@RequestParam("categoryId") Long categoryID) {
		Product product = new Product();
		product.setName(name);
		product.setAccount(account);
		product.setPrice(price);

		return productService.save(product, categoryID);

	}
}
