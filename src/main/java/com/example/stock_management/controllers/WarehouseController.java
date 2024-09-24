package com.example.stock_management.controllers;

import com.example.stock_management.dtos.CreateWarehouseRequestDTO;
import com.example.stock_management.exceptions.FileNotUploadedException;
import com.example.stock_management.exceptions.ResourceNotFoundException;
import com.example.stock_management.filters.WarehouseFilter;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public Page<Warehouse> getAllWarehouses(
            @Valid WarehouseFilter warehouseFilter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int perPage
    ) {
        return warehouseService.getPaginatedFilteredWarehouses(warehouseFilter, page, perPage);
    }

    @PostMapping(consumes = "multipart/form-data")
    public Warehouse addWarehouse(@Valid @ModelAttribute CreateWarehouseRequestDTO warehousePayload) throws IOException, FileNotUploadedException {
        return warehouseService.addWarehouse(warehousePayload);
    }

    @GetMapping("/{warehouseId}")
    public Warehouse getWarehouse(@PathVariable int warehouseId) throws ResourceNotFoundException {
        return warehouseService.getWarehouseById(warehouseId);
    }

    @DeleteMapping("/{warehouseId}")
    public void deleteWarehouse(@PathVariable int warehouseId) throws IOException, ResourceNotFoundException {
        warehouseService.deleteWarehouse(warehouseId);
    }

    @PutMapping("/{warehouseId}")
    public Warehouse updateWarehouse(@PathVariable int warehouseId, @RequestBody Map<String, Object> warehouseData) throws ResourceNotFoundException {
        return warehouseService.updateWarehouse(warehouseId, warehouseData);
    }

}
