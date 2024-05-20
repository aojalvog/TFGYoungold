package com.tfg.youngold.services;

import org.springframework.http.ResponseEntity;

import com.tfg.youngold.response.CategoryResponseRest;

public interface ICategoryService {

	public ResponseEntity<CategoryResponseRest> search();
}
