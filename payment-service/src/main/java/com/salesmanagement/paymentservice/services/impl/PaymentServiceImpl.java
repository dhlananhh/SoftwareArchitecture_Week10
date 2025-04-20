package com.salesmanagement.paymentservice.services.impl;


import com.salesmanagement.paymentservice.clients.OrderClient;
import com.salesmanagement.paymentservice.dto.OrderDto;
import com.salesmanagement.paymentservice.entities.Payment;
import com.salesmanagement.paymentservice.repositories.PaymentRepository;
import com.salesmanagement.paymentservice.services.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    @Override
    public Payment processPayment(Payment payment) {
        OrderDto order = orderClient.getOrderById(payment.getOrderId());
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Order is not in PENDING status");
        }
        payment.setStatus("COMPLETED");
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment refundPayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            if ("COMPLETED".equals(payment.getStatus())) {
                payment.setStatus("REFUNDED");
                return paymentRepository.save(payment);
            }
            throw new RuntimeException("Payment cannot be refunded");
        }
        throw new RuntimeException("Payment not found");
    }
}