package com.tfg.youngold.controller;

import org.springframework.http.ResponseEntity;
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
	private ResponseEntity<CategoryResponseRest> searchCategories() {
		ResponseEntity<CategoryResponseRest> response = service.search();
		return response;
	}

	@GetMapping("/categories/{id}")
	private ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id) {

		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		return response;
	}

	// Post

	@PostMapping("/categories")
	private ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
		ResponseEntity<CategoryResponseRest> response = service.save(category);
		return response;
	}

	// PUT

	@PutMapping("/categories/{id}")
	private ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {
		ResponseEntity<CategoryResponseRest> response = service.update(category, id);
		return response;
	}

}
