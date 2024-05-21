package com.tfg.youngold.response;

import java.util.List;

import com.tfg.youngold.model.Product;

import lombok.Data;

@Data
public class ProductResponse {

	List<Product> products;

}
