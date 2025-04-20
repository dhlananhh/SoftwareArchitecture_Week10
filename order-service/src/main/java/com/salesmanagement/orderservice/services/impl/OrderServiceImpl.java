package com.salesmanagement.orderservice.services.impl;


import com.salesmanagement.orderservice.clients.InventoryClient;
import com.salesmanagement.orderservice.clients.InventoryUpdateDto;
import com.salesmanagement.orderservice.clients.ProductClient;
import com.salesmanagement.orderservice.dto.ProductDto;
import com.salesmanagement.orderservice.entities.Order;
import com.salesmanagement.orderservice.repositories.OrderRepository;
import com.salesmanagement.orderservice.services.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    @Override
    public Order createOrder(Order order) {
        ProductDto product = productClient.getProductById(order.getProductId());
        if (product.getStock() < order.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        order.setTotalPrice(product.getPrice() * order.getQuantity());
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        inventoryClient.updateInventory(product.getId(), new InventoryUpdateDto(product.getStock() - order.getQuantity()));
        return savedOrder;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order cancelOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if ("PENDING".equals(order.getStatus())) {
                order.setStatus("CANCELLED");
                ProductDto product = productClient.getProductById(order.getProductId());
                inventoryClient.updateInventory(product.getId(), new InventoryUpdateDto(product.getStock() + order.getQuantity()));
                return orderRepository.save(order);
            }
            throw new RuntimeException("Order cannot be cancelled");
        }
        throw new RuntimeException("Order not found");
    }
}