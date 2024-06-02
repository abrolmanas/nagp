package com.nagp.springboot_mongodb.repository;

import com.nagp.springboot_mongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, Integer> {


}
