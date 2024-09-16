package com.example.stock_management.repo;

import com.example.stock_management.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepo extends JpaRepository<Warehouse, Integer> {
}
