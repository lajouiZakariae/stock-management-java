package com.example.stock_management.controllers;

import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repo.WarehouseRepo;
import com.example.stock_management.services.WarehouseService;
import com.example.stock_management.speceifications.WarehouseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    WarehouseRepo warehouseRepo;

    @GetMapping("/warehouses")
    public Page<Warehouse> getAllWarehouses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int perPage
    ) {
         Specification<Warehouse> specification = WarehouseSpecification.buildSpecification(name);

        return warehouseRepo.findAll(specification,PageRequest.of(page - 1, perPage));
    }

    @PostMapping("/warehouses")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/warehouses/{warehouseId}")
    public Warehouse getWarehouse(@PathVariable int warehouseId) {
        return warehouseService.getWarehouseById(warehouseId);
    }

    @DeleteMapping("/warehouses/{warehouseId}")
    public void deleteWarehouse(@PathVariable int warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
    }

    @PutMapping("/warehouses/{warehouseId}")
    public Warehouse updateWarehouse(@PathVariable int warehouseId, @RequestBody Map<String,Object> warehouseData) {
        return warehouseService.updateWarehouse(warehouseId,warehouseData);
    }

}
