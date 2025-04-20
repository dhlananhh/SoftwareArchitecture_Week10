package com.salesmanagement.inventoryservice.repositories;

import com.salesmanagement.inventoryservice.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
}