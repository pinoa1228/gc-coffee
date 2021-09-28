package com.example.gccoffee.controller;


import com.example.gccoffee.Model.Category;
import com.example.gccoffee.Model.Product;
import com.example.gccoffee.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductRestController {
    private  final ProductService productService;
    public ProductRestController(ProductService productService){
        this.productService=productService;
    }


    @GetMapping("api/v1/products")
    public List<Product> productsList(@RequestParam Optional<Category> category){
        //RequusetParm을 이용해서 카테고리별로 조회

        return category
                .map(productService::getProductsByCategory)
                .orElse(productService.getAllProducts());


    }


}

