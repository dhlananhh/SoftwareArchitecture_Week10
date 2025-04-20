package com.salesmanagement.shippingservice.clients;

import com.salesmanagement.shippingservice.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "http://order-service:8082/api/orders")
public interface OrderClient {
    @GetMapping("/{id}")
    OrderDto getOrderById(@PathVariable Long id);
}