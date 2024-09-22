package com.example.stock_management.repo;

import com.example.stock_management.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {
}
