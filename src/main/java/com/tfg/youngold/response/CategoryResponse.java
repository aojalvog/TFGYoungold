package com.tfg.youngold.response;

import java.util.List;

import com.tfg.youngold.model.Category;

import lombok.Data;

@Data
public class CategoryResponse {

	private List<Category> category;
}
