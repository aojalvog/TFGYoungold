package com.tfg.youngold.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.youngold.response.CategoryResponseRest;
import com.tfg.youngold.services.ICategoryService;

@RestController
@RequestMapping("/youngold")
public class CategoryController {

	private ICategoryService service;

	public CategoryController(ICategoryService service) {
		this.service = service;
	}

	/**
	 * Devuelve todas las categorías
	 * 
	 * @return
	 */

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

}
