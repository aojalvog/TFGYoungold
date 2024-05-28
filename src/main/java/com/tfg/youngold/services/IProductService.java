package com.tfg.youngold.services;

import org.springframework.http.ResponseEntity;

import com.tfg.youngold.model.Product;
import com.tfg.youngold.response.ProductResponseRest;

public interface IProductService {

	public ResponseEntity<ProductResponseRest> save(Product producto, Long categoryId);

	public ResponseEntity<ProductResponseRest> searchById(Long id);

	public ResponseEntity<ProductResponseRest> searchByName(String name);

	public ResponseEntity<ProductResponseRest> delete(Long id);

	public ResponseEntity<ProductResponseRest> search();

	public ResponseEntity<ProductResponseRest> update(Product producto, Long categoryId, Long id);

}
