package com.example.gccoffee.Repository;

import com.example.gccoffee.Model.Category;
import com.example.gccoffee.Model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAll();
    Product insert(Product product);
    Product update(Product product);
    Optional<Product> findById(UUID productid);
    Optional<Product> findByName(String productName);
    List<Product> findbyCategory(Category category);
    void deleteAll();
}
