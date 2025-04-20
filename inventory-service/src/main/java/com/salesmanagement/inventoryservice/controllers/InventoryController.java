package com.salesmanagement.inventoryservice.controllers;


import com.salesmanagement.inventoryservice.entities.Inventory;
import com.salesmanagement.inventoryservice.services.InventoryService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    @CircuitBreaker(name = "inventoryService")
    @Retry(name = "inventoryService")
    @RateLimiter(name = "inventoryService")
    @TimeLimiter(name = "inventoryService")
    public CompletableFuture<Inventory> addInventory(@RequestBody Inventory inventory) {
        return CompletableFuture.supplyAsync(() -> inventoryService.addInventory(inventory));
    }

    @GetMapping("/{productId}")
    @CircuitBreaker(name = "inventoryService")
    @Retry(name = "inventoryService")
    @RateLimiter(name = "inventoryService")
    @TimeLimiter(name = "inventoryService")
    public CompletableFuture<ResponseEntity<Inventory>> getInventoryByProductId(@PathVariable Long productId) {
        return CompletableFuture.supplyAsync(() -> inventoryService.getInventoryByProductId(productId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @PutMapping("/{productId}")
    @CircuitBreaker(name = "inventoryService")
    @Retry(name = "inventoryService")
    @RateLimiter(name = "inventoryService")
    @TimeLimiter(name = "inventoryService")
    public CompletableFuture<Inventory> updateInventory(@PathVariable Long productId, @RequestBody Inventory inventory) {
        return CompletableFuture.supplyAsync(() -> inventoryService.updateInventory(productId, inventory));
    }
}