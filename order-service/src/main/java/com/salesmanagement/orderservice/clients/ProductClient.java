package com.salesmanagement.orderservice.clients;


import com.salesmanagement.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "product-service", url = "http://product-service:8081/api/products")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable Long id);
}