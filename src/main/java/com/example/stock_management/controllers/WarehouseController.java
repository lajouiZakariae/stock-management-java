package com.example.stock_management.controllers;

import com.example.stock_management.exceptions.ResourceNotFoundException;
import com.example.stock_management.filters.WarehouseFilter;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repo.WarehouseRepo;
import com.example.stock_management.services.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value = "/warehouses")
@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseRepo warehouseRepo;
    private final WarehouseService warehouseService;
    private final PagedResourcesAssembler<Warehouse> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<EntityModel<Warehouse>> getAllWarehouses(
            @Valid WarehouseFilter warehouseFilter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int perPage
    ) {
        Page<Warehouse> wareHousePage = warehouseService.getPaginatedFilteredWarehouses(warehouseFilter,page,perPage);

        return pagedResourcesAssembler.toModel(wareHousePage);
    }

    @PostMapping("/")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/{warehouseId}")
    public Warehouse getWarehouse(@PathVariable int warehouseId) throws ResourceNotFoundException {
        return warehouseService.getWarehouseById(warehouseId);
    }

    @DeleteMapping("/{warehouseId}")
    public void deleteWarehouse(@PathVariable int warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
    }

    @PutMapping("/{warehouseId}")
    public Warehouse updateWarehouse(@PathVariable int warehouseId, @RequestBody Map<String,Object> warehouseData) throws ResourceNotFoundException {
        return warehouseService.updateWarehouse(warehouseId,warehouseData);
    }

}
