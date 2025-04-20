package com.salesmanagement.orderservice.controllers;


import com.salesmanagement.orderservice.entities.Order;
import com.salesmanagement.orderservice.services.OrderService;

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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "orderService")
    @Retry(name = "orderService")
    @RateLimiter(name = "orderService")
    @TimeLimiter(name = "orderService")
    public CompletableFuture<Order> createOrder(@RequestBody Order order) {
        return CompletableFuture.supplyAsync(() -> orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "orderService")
    @Retry(name = "orderService")
    @RateLimiter(name = "orderService")
    @TimeLimiter(name = "orderService")
    public CompletableFuture<ResponseEntity<Order>> getOrderById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping
    @CircuitBreaker(name = "orderService")
    @Retry(name = "orderService")
    @RateLimiter(name = "orderService")
    @TimeLimiter(name = "orderService")
    public CompletableFuture<List<Order>> getAllOrders() {
        return CompletableFuture.supplyAsync(() -> orderService.getAllOrders());
    }

    @PutMapping("/{id}/cancel")
    @CircuitBreaker(name = "orderService")
    @Retry(name = "orderService")
    @RateLimiter(name = "orderService")
    @TimeLimiter(name = "orderService")
    public CompletableFuture<Order> cancelOrder(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> orderService.cancelOrder(id));
    }
}