package com.tfg.youngold.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.youngold.model.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
