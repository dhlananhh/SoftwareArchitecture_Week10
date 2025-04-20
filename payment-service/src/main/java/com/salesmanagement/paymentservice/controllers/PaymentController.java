package com.salesmanagement.paymentservice.controllers;


import com.salesmanagement.paymentservice.entities.Payment;
import com.salesmanagement.paymentservice.services.PaymentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import lombok.RequiredArgsConstructor;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @CircuitBreaker(name = "paymentService")
    @Retry(name = "paymentService")
    @RateLimiter(name = "paymentService")
    @TimeLimiter(name = "paymentService")
    public CompletableFuture<Payment> processPayment(@RequestBody Payment payment) {
        return CompletableFuture.supplyAsync(() -> paymentService.processPayment(payment));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "paymentService")
    @Retry(name = "paymentService")
    @RateLimiter(name = "paymentService")
    @TimeLimiter(name = "paymentService")
    public CompletableFuture<ResponseEntity<Payment>> getPaymentById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/{id}/refund")
    @CircuitBreaker(name = "paymentService")
    @Retry(name = "paymentService")
    @RateLimiter(name = "paymentService")
    @TimeLimiter(name = "paymentService")
    public CompletableFuture<Payment> refundPayment(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> paymentService.refundPayment(id));
    }
}