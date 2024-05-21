package com.tfg.youngold.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.youngold.model.Category;

public interface ICategoryDao extends CrudRepository<Category, Long> {

}
