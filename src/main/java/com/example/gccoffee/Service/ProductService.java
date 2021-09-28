package com.example.gccoffee.Service;

import com.example.gccoffee.Model.Category;
import com.example.gccoffee.Model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByCategory(Category category);
    List<Product> getAllProducts();

    Product createProduct(String productName,Category category,long price);
    Product createProduct(String productName,Category category,long price,String description);


}
