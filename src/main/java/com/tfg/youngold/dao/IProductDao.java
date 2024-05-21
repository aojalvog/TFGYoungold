package com.tfg.youngold.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tfg.youngold.model.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

	@Query("select p from Product p where p.name like %?1%")
	List<Product> findByNameLike(String name);

	List<Product> findByNameContainingIgnoreCase(String name);
}
