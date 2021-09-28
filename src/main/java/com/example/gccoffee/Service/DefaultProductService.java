package com.example.gccoffee.Service;

import com.example.gccoffee.Model.Category;
import com.example.gccoffee.Model.Product;
import com.example.gccoffee.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findbyCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, long price) {
        var product=new Product(UUID.randomUUID(),productName,category,price);
        return productRepository.insert(product);

    }

    @Override
    public Product createProduct(String productName, Category category, long price, String description) {
        var product=new Product(UUID.randomUUID(),productName,category,price,description, LocalDateTime.now(),LocalDateTime.now());
        return productRepository.insert(product);
    }
}
