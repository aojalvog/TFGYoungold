package com.tfg.youngold.services;

import org.springframework.http.ResponseEntity;

import com.tfg.youngold.model.Product;
import com.tfg.youngold.response.ProductResponseRest;

public interface IProductService {

	public ResponseEntity<ProductResponseRest> save(Product producto, Long categoryId);
}
