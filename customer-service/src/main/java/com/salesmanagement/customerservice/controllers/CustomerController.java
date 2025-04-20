package com.salesmanagement.customerservice.controllers;


import com.salesmanagement.customerservice.entities.Customer;
import com.salesmanagement.customerservice.services.CustomerService;

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
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @CircuitBreaker(name = "customerService")
    @Retry(name = "customerService")
    @RateLimiter(name = "customerService")
    @TimeLimiter(name = "customerService")
    public CompletableFuture<Customer> createCustomer(@RequestBody Customer customer) {
        return CompletableFuture.supplyAsync(() -> customerService.createCustomer(customer));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "customerService")
    @Retry(name = "customerService")
    @RateLimiter(name = "customerService")
    @TimeLimiter(name = "customerService")
    public CompletableFuture<ResponseEntity<Customer>> getCustomerById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping
    @CircuitBreaker(name = "customerService")
    @Retry(name = "customerService")
    @RateLimiter(name = "customerService")
    @TimeLimiter(name = "customerService")
    public CompletableFuture<List<Customer>> getAllCustomers() {
        return CompletableFuture.supplyAsync(() -> customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "customerService")
    @Retry(name = "customerService")
    @RateLimiter(name = "customerService")
    @TimeLimiter(name = "customerService")
    public CompletableFuture<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return CompletableFuture.supplyAsync(() -> customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "customerService")
    @Retry(name = "customerService")
    @RateLimiter(name = "customerService")
    @TimeLimiter(name = "customerService")
    public CompletableFuture<Void> deleteCustomer(@PathVariable Long id) {
        return CompletableFuture.runAsync(() -> customerService.deleteCustomer(id));
    }
}