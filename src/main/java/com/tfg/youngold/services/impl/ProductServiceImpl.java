package com.tfg.youngold.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tfg.youngold.dao.ICategoryDao;
import com.tfg.youngold.dao.IProductDao;
import com.tfg.youngold.model.Category;
import com.tfg.youngold.model.Product;
import com.tfg.youngold.response.ProductResponseRest;
import com.tfg.youngold.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	private ICategoryDao categoryDao;

	public ProductServiceImpl(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;

	}

	@Autowired
	private IProductDao productDao;

	@Override
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();

		try {
			Optional<Category> category = categoryDao.findById(categoryId);

			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("Respues inválida", "-1", "Categoría no encontrada");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			Product productSaved = productDao.save(product);

			if (productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta OK", "00", "Producto guardado");
			} else {
				response.setMetadata("Respuesta inválida", "-1", "Producto no guardado");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al guardar el producto");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
