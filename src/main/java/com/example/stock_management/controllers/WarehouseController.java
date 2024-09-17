package com.example.stock_management.controllers;

import com.example.stock_management.models.Warehouse;
import com.example.stock_management.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public void getAllWarehouses(@RequestParam Map<String,Object> params) {
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
