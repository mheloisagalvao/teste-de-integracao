package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("Product not found for id: " + id);
        }
    }

    @PostMapping("/add")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
