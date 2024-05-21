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

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements IProductService {

	private ICategoryDao categoryDao;

	public ProductServiceImpl(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;

	}

	@Autowired
	private IProductDao productDao;

	@Override
	@Transactional
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

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> searchById(Long id) {

		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();

		try {
			Optional<Product> product = productDao.findById(id);

			if (product.isPresent()) {
				list.add(product.get());
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta OK", "00", "Producto encontrado");
			} else {
				response.setMetadata("Respues inválida", "-1", "Producto no encontrado");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al encontrar el producto");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		try {
			listAux = productDao.findByNameContainingIgnoreCase(name);

			if (listAux.size() > 0) {

				listAux.stream().forEach((p) -> {
					list.add(p);
				});

				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta OK", "00", "Productos encontrados");
			} else {
				response.setMetadata("Respues inválida", "-1", "Producto no encontrado");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al buscar el producto");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ProductResponseRest> deleteById(Long id) {
		ProductResponseRest response = new ProductResponseRest();

		try {
			productDao.deleteById(id);

		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al eliminar el producto");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> search() {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		try {
			listAux = (List<Product>) productDao.findAll();

			if (listAux.size() > 0) {

				listAux.stream().forEach((p) -> {
					list.add(p);
				});

				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta OK", "00", "Productos encontrados");
			} else {
				response.setMetadata("Respues inválida", "-1", "Productos no encontrados");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al buscar el productos");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
