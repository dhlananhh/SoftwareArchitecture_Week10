package com.salesmanagement.inventoryservice.services;

import com.salesmanagement.inventoryservice.entities.Inventory;
import java.util.Optional;

public interface InventoryService {
    Inventory addInventory(Inventory inventory);
    Optional<Inventory> getInventoryByProductId(Long productId);
    Inventory updateInventory(Long productId, Inventory inventory);
}