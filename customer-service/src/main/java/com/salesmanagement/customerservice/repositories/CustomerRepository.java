package com.salesmanagement.customerservice.repositories;

import com.salesmanagement.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}