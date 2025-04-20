package com.salesmanagement.paymentservice.repositories;

import com.salesmanagement.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
}