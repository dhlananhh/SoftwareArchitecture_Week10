package com.salesmanagement.orderservice.clients;

public class InventoryUpdateDto {
	private Integer stock;

    public InventoryUpdateDto(Integer stock) {
        this.stock = stock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
