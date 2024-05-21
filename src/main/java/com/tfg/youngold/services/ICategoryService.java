package com.tfg.youngold.services;

import org.springframework.http.ResponseEntity;

import com.tfg.youngold.model.Category;
import com.tfg.youngold.response.CategoryResponseRest;

public interface ICategoryService {

	// GET

	public ResponseEntity<CategoryResponseRest> search();

	public ResponseEntity<CategoryResponseRest> searchById(Long id);

	// POST

	public ResponseEntity<CategoryResponseRest> save(Category category);

	// PUT

	public ResponseEntity<CategoryResponseRest> update(Category category, Long id);

	// DELETE

	public ResponseEntity<CategoryResponseRest> delete(Long id);

}
