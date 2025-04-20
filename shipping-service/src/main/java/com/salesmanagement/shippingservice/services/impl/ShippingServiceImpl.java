package com.salesmanagement.shippingservice.services.impl;


import com.salesmanagement.shippingservice.clients.OrderClient;
import com.salesmanagement.shippingservice.dto.OrderDto;
import com.salesmanagement.shippingservice.entities.Shipping;
import com.salesmanagement.shippingservice.repositories.ShippingRepository;
import com.salesmanagement.shippingservice.services.ShippingService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {
    private final ShippingRepository shippingRepository;
    private final OrderClient orderClient;

    @Override
    public Shipping createShipping(Shipping shipping) {
        OrderDto order = orderClient.getOrderById(shipping.getOrderId());
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Order is not in PENDING status");
        }
        shipping.setStatus("PENDING");
        return shippingRepository.save(shipping);
    }

    @Override
    public Optional<Shipping> getShippingById(Long id) {
        return shippingRepository.findById(id);
    }

    @Override
    public Shipping updateShippingStatus(Long id, String status) {
        Optional<Shipping> shippingOptional = shippingRepository.findById(id);
        if (shippingOptional.isPresent()) {
            Shipping shipping = shippingOptional.get();
            if ("PENDING".equals(shipping.getStatus()) && "SHIPPED".equals(status) ||
                "SHIPPED".equals(shipping.getStatus()) && "DELIVERED".equals(status)) {
                shipping.setStatus(status);
                return shippingRepository.save(shipping);
            }
            throw new RuntimeException("Invalid status transition");
        }
        throw new RuntimeException("Shipping not found");
    }
}