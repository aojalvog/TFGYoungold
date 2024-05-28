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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

	private ICategoryDao categoryDao;

	public ProductServiceImpl(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;

	}

	@Autowired
	private IProductDao productDao;

	// GET

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> search() {
		log.info("<---Entrando en el método search--->");
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

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> searchById(Long id) {
		log.info("<---Entrando en el método searchById--->");

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
		log.info("<---Entrando en el método searcgByName--->");
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

	// POST

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		log.info("<---Entrando en el método save--->");
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();

		try {
			Optional<Category> category = categoryDao.findById(categoryId);

			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("Respuesta inválida", "-1", "Categoría no encontrada");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			// Buscar el producto existente en la base de datos por nombre y categoría
			Optional<Product> existingProduct = productDao.findByNameAndCategory(product.getName(),
					product.getCategory());

			if (existingProduct.isPresent()) {
				// Actualizar la cantidad del producto existente
				Product existingProductEntity = existingProduct.get();
				existingProductEntity.setPrice(((existingProductEntity.getPrice() * existingProductEntity.getAccount())
						+ (product.getPrice() * product.getAccount()))
						/ (product.getAccount() + existingProductEntity.getAccount()));
				existingProductEntity.setAccount(existingProductEntity.getAccount() + product.getAccount());
				productDao.save(existingProductEntity);
			} else {
				// Crear un nuevo registro si el producto no existe
				Product productSaved = productDao.save(product);
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta OK", "00", "Producto guardado");
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al guardar el producto");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// PUT

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id) {
		log.info("<---Entrando en el método update--->");
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

			Optional<Product> productSearch = productDao.findById(id);

			if (productSearch != null) {
				productSearch.get().setName(product.getName());
				productSearch.get().setCategory(product.getCategory());
				productSearch.get().setAccount(product.getAccount());
				productSearch.get().setPrice(product.getPrice());

				Product productoToUpdate = productDao.save(productSearch.get());

				if (productoToUpdate != null) {
					list.add(productoToUpdate);
					response.getProduct().setProducts(list);
					response.setMetadata("Respuesta OK", "00", "Producto actualizado");
				} else {
					response.setMetadata("Respuesta inválida", "-1", "Producto no actualizado");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setMetadata("Respuesta inválida", "-1", "Producto no actualizado");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al actualizar el producto");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// DELETE

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> delete(Long id) {
		log.info("<---Entrando en el método delete--->");
		ProductResponseRest response = new ProductResponseRest();

		try {
			productDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Producto eliminado");

		} catch (Exception e) {
			response.setMetadata("Respuesta inválida", "-1", "Error al eliminar el producto");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
