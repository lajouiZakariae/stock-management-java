package com.example.stock_management.repositories;

import com.example.stock_management.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {
}
