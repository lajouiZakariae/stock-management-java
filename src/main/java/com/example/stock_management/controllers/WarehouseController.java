package com.example.stock_management.controllers;

import com.example.stock_management.filters.WarehouseFilter;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repo.WarehouseRepo;
import com.example.stock_management.services.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(value = "/warehouses")
@RestController
@Validated
public class WarehouseController {

    WarehouseRepo warehouseRepo;

    WarehouseService warehouseService;

    public WarehouseController(WarehouseRepo warehouseRepo, WarehouseService warehouseService) {
        super();
        this.warehouseRepo = warehouseRepo;
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public WarehouseFilter getAllWarehouses(
            @Valid WarehouseFilter warehouseFilter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int perPage
    ) {
        // Specification<Warehouse> specification = WarehouseSpecification.buildSpecification(name);

        // return warehouseRepo.findAll(specification,PageRequest.of(page - 1, perPage));
        return warehouseFilter;
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
