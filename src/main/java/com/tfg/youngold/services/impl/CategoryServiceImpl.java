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

@Service
public class CategoryServiceImpl implements ICategoryService {

	private ICategoryDao categoryDao;

	public CategoryServiceImpl(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;

	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> search() {

		CategoryResponseRest response = new CategoryResponseRest();
		try {
			List<Category> category = (List<Category>) categoryDao.findAll();

			response.getCategoryResponse().setCategory(category);
			response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");

		} catch (Exception e) {
			response.setMetadata("Respuesta incorrecta", "-1", "Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
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
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			response.setMetadata("Respuesta incorrecta", "-1", "Error al consultar por ID");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

}
