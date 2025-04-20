package com.salesmanagement.orderservice.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "inventory-service", url = "http://inventory-service:8085/api/inventory")
public interface InventoryClient {
    @PutMapping("/{productId}")
    void updateInventory(@PathVariable Long productId, @RequestBody InventoryUpdateDto inventoryUpdateDto);
}
