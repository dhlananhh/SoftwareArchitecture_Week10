package com.salesmanagement.productservice.controllers;


import com.salesmanagement.productservice.entities.Product;
import com.salesmanagement.productservice.services.ProductService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    @TimeLimiter(name = "productService")
    public CompletableFuture<Product> createProduct(@RequestBody Product product) {
        return CompletableFuture.supplyAsync(() -> productService.createProduct(product));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    @TimeLimiter(name = "productService")
    public CompletableFuture<ResponseEntity<Product>> getProductById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    @TimeLimiter(name = "productService")
    public CompletableFuture<List<Product>> getAllProducts() {
        return CompletableFuture.supplyAsync(() -> productService.getAllProducts());
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    @TimeLimiter(name = "productService")
    public CompletableFuture<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return CompletableFuture.supplyAsync(() -> productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "productService")
    @Retry(name = "productService")
    @RateLimiter(name = "productService")
    @TimeLimiter(name = "productService")
    public CompletableFuture<Void> deleteProduct(@PathVariable Long id) {
        return CompletableFuture.runAsync(() -> productService.deleteProduct(id));
    }
}