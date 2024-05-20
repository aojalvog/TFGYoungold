package com.tfg.youngold.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.youngold.model.Category;
import com.tfg.youngold.response.CategoryResponseRest;
import com.tfg.youngold.services.ICategoryService;

@RestController
@RequestMapping("/youngold")
public class CategoryController {

	private ICategoryService service;

	public CategoryController(ICategoryService service) {
		this.service = service;
	}

	// Get

	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategories() {

		return service.search();
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id) {

		return service.searchById(id);
	}

	// Post

	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {

		return service.save(category);

	}

	// PUT

	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {
		return service.update(category, id);
	}

	// DELETE

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id) {

		return service.delete(id);
	}

}
