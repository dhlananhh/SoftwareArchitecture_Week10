package com.salesmanagement.inventoryservice.services.impl;


import com.salesmanagement.inventoryservice.entities.Inventory;
import com.salesmanagement.inventoryservice.repositories.InventoryRepository;
import com.salesmanagement.inventoryservice.services.InventoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findById(productId);
    }

    @Override
    public Inventory updateInventory(Long productId, Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryRepository.findById(productId);
        if (existingInventory.isPresent()) {
            Inventory updatedInventory = existingInventory.get();
            updatedInventory.setStock(inventory.getStock());
            return inventoryRepository.save(updatedInventory);
        }
        throw new RuntimeException("Inventory not found");
    }
}