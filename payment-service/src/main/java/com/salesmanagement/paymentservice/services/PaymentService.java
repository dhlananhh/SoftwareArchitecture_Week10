package com.salesmanagement.paymentservice.services;

import com.salesmanagement.paymentservice.entities.Payment;
import java.util.Optional;

public interface PaymentService {
    Payment processPayment(Payment payment);
    Optional<Payment> getPaymentById(Long id);
    Payment refundPayment(Long id);
}