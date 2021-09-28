package com.example.gccoffee.controller;

import com.example.gccoffee.Model.Category;
import com.example.gccoffee.Model.Product;
import com.example.gccoffee.Model.ProductDto;
import com.example.gccoffee.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {
    private  final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @GetMapping("/products")
    public  String productspage(Model model){

        var products = productService.getAllProducts();
        model.addAttribute("products",products);
        return "products-list";
    }
    @GetMapping("/product/new")
    public String newgetproductpage(){
        return "new-product";
    }
    @PostMapping("/product/new")
    public String newproductpage(ProductDto productDto){

        productService.createProduct(productDto.productName(),productDto.category(),productDto.price(),productDto.description());
        return "redirect:/products";
    }
}
