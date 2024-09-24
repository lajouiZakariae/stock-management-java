package com.example.stock_management.controllers;

import com.example.stock_management.dtos.CreateWarehouseRequestDTO;
import com.example.stock_management.dtos.UpdateWarehouseRequestDto;
import com.example.stock_management.dtos.WarehouseFilter;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public Warehouse addWarehouse(@Valid @ModelAttribute CreateWarehouseRequestDTO warehousePayload) {
        return warehouseService.addWarehouse(warehousePayload);
    }

    @GetMapping("/{warehouseId}")
    public Warehouse getWarehouse(@PathVariable int warehouseId) {
        return warehouseService.getWarehouseById(warehouseId);
    }

    @DeleteMapping("/{warehouseId}")
    public void deleteWarehouse(@PathVariable int warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
    }

    @PutMapping("/{warehouseId}")
    public Warehouse updateWarehouse(@PathVariable int warehouseId, @Valid UpdateWarehouseRequestDto warehouseData) {
         return warehouseService.updateWarehouse(warehouseId, warehouseData);
    }

}
