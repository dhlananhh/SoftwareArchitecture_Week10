package com.salesmanagement.shippingservice.services;

import com.salesmanagement.shippingservice.entities.Shipping;
import java.util.Optional;

public interface ShippingService {
    Shipping createShipping(Shipping shipping);
    Optional<Shipping> getShippingById(Long id);
    Shipping updateShippingStatus(Long id, String status);
}