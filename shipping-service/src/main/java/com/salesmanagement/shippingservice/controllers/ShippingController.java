package com.salesmanagement.shippingservice.controllers;


import com.salesmanagement.shippingservice.clients.StatusUpdateDto;
import com.salesmanagement.shippingservice.entities.Shipping;
import com.salesmanagement.shippingservice.services.ShippingService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/shippings")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;

    @PostMapping
    @CircuitBreaker(name = "shippingService")
    @Retry(name = "shippingService")
    @RateLimiter(name = "shippingService")
    @TimeLimiter(name = "shippingService")
    public CompletableFuture<Shipping> createShipping(@RequestBody Shipping shipping) {
        return CompletableFuture.supplyAsync(() -> shippingService.createShipping(shipping));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "shippingService")
    @Retry(name = "shippingService")
    @RateLimiter(name = "shippingService")
    @TimeLimiter(name = "shippingService")
    public CompletableFuture<ResponseEntity<Shipping>> getShippingById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> shippingService.getShippingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "shippingService")
    @Retry(name = "shippingService")
    @RateLimiter(name = "shippingService")
    @TimeLimiter(name = "shippingService")
    public CompletableFuture<Shipping> updateShippingStatus(@PathVariable Long id, @RequestBody StatusUpdateDto statusUpdateDto) {
        return CompletableFuture.supplyAsync(() -> shippingService.updateShippingStatus(id, statusUpdateDto.getStatus()));
    }
}