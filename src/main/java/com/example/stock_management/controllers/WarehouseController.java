package com.example.stock_management.controllers;

import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repo.WarehouseRepo;
import com.example.stock_management.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    WarehouseRepo warehouseRepo;

    @GetMapping("/warehouses")
    public List<Warehouse> getAllWarehouses() {
//        List<Warehouse> warehouses = new ArrayList<>(List.of(
//                new Warehouse(103,"Tanger",25f,35f),
//                new Warehouse(104,"Casablanca",35f,45f)
//        ));

//        warehouseRepo.saveAll(warehouses);

        return warehouseService.getAllWarehouses();
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
    public Warehouse updateWarehouse(@PathVariable int warehouseId,@RequestBody Warehouse warehouse) {

        warehouse.setId(warehouseId);

        return warehouseService.updateWarehouse(warehouse);
    }

}
