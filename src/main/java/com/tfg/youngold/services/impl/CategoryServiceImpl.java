package com.tfg.youngold.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tfg.youngold.dao.ICategoryDao;
import com.tfg.youngold.model.Category;
import com.tfg.youngold.response.CategoryResponseRest;
import com.tfg.youngold.services.ICategoryService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

	private ICategoryDao categoryDao;

	public CategoryServiceImpl(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;

	}

	// GET
	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> search() {

		log.info("<---Entrando en el método search--->");
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			List<Category> category = (List<Category>) categoryDao.findAll();

			response.getCategoryResponse().setCategory(category);
			response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");

		} catch (Exception e) {
			response.setMetadata("Respuesta incorrecta", "-1", "Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		log.info("<---Entrando en el método searchById--->");
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {
			Optional<Category> category = categoryDao.findById(id);
			if (category.isPresent()) {
				list.add(category.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta OK", "00", "Categoría encontrada");
			} else {
				response.setMetadata("Respuesta incorrecta", "-1", "Categoría no encontrada");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			response.setMetadata("Respuesta incorrecta", "-1", "Error al consultar por ID");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// POST

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		log.info("<---Entrando en el método save--->");
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {

			// Cambiar por un optional para que guarde una vez
			Category categorySaved = categoryDao.save(category);
			if (categorySaved != null) {
				list.add(categorySaved);
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta correcta", "00", "Categoría guardada");

			} else {
				response.setMetadata("Respuesta incorrecta", "-1", "Categoría no guardada");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (

		Exception e) {
			response.setMetadata("Respuesta incorrecta", "-1", "Error al guardar la categoría");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// PUT

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
		log.info("<---Entrando en el método update--->");
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {

			Optional<Category> categorySearch = categoryDao.findById(id);

			if (categorySearch.isPresent()) {
				categorySearch.get().setName(category.getName());

				Category categoryToUpdate = categoryDao.save(categorySearch.get());

				if (categoryToUpdate != null) {

					list.add(categoryToUpdate);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata("Respuesta OK", "00", "Categoría actualizada");

				} else {
					response.setMetadata("Respuesta incorrecta", "-1", "Categoría no actualizada");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}

			} else {

				response.setMetadata("Respuesta incorrecta", "-1", "Categoría no encontrada");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (

		Exception e) {
			response.setMetadata("Respuesta incorrecta", "-1", "Error al guardar la categoría");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// DELETE

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> delete(Long id) {
		log.info("<---Entrando en el método delete--->");
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			categoryDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Registro eliminado");
		} catch (Exception e) {
			response.setMetadata("Respuesta incorrecta", "-1", "Error al eliminar");
			e.getStackTrace();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
