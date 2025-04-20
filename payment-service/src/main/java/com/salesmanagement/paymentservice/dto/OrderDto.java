package com.salesmanagement.paymentservice.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;
    private String status;
}