package com.salesmanagement.shippingservice.repositories;

import com.salesmanagement.shippingservice.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
	
}