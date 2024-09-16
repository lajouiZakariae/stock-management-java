package com.example.stock_management.repo;

import com.example.stock_management.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepo extends JpaRepository<Warehouse, Integer> {

    List<Warehouse> findByName(String name);

}
