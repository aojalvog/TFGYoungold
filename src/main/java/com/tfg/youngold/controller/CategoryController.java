package com.tfg.youngold.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.youngold.model.Category;
import com.tfg.youngold.response.CategoryResponseRest;
import com.tfg.youngold.services.ICategoryService;
import com.tfg.youngold.util.CategoryExcelExport;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/youngold")
public class CategoryController {

	private ICategoryService service;

	public CategoryController(ICategoryService service) {
		this.service = service;
	}

	// Get

	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategories() {
		log.info("<---Entrando en método searchCategories--->");
		return service.search();
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id) {
		log.info("<---Entrando en método searchCategoriesById--->");
		return service.searchById(id);
	}

	// Post

	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
		log.info("<---Entrando en método save--->");
		return service.save(category);

	}

	// PUT

	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {
		log.info("<---Entrando en método update--->");
		return service.update(category, id);
	}

	// DELETE

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id) {
		log.info("<---Entrando en método delete--->");
		return service.delete(id);
	}

	// Export

	@GetMapping("/categories/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=result_category.xlsx";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<CategoryResponseRest> categoryResponse = service.search();

		CategoryExcelExport excelExporter = new CategoryExcelExport(
				categoryResponse.getBody().getCategoryResponse().getCategory());

		excelExporter.export(response);

	}

}
