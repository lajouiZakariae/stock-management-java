package com.example.stock_management.services;

import com.example.stock_management.exceptions.ResourceNotFoundException;
import com.example.stock_management.filters.WarehouseFilter;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repo.WarehouseRepo;
import com.example.stock_management.speceifications.WarehouseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class WarehouseService {

    WarehouseRepo warehouseRepo;

    public WarehouseService(WarehouseRepo warehouseRepo) {
        super();
        this.warehouseRepo = warehouseRepo;
    }

    public Page<Warehouse> getPaginatedFilteredWarehouses(WarehouseFilter warehouseFilter, int page, int perPage) {
        Specification<Warehouse> warehouseSpecification = Specification.where(null);

        if (StringUtils.hasText(warehouseFilter.getName())) {
            warehouseSpecification = warehouseSpecification.and(WarehouseSpecification.hasNameLike(warehouseFilter.getName()));
        }

        return warehouseRepo.findAll(warehouseSpecification, PageRequest.of(page,perPage));
    }

    public Warehouse getWarehouseById(Integer id) throws ResourceNotFoundException {
        return warehouseRepo
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Warehouse not found"));
    }

    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public Warehouse updateWarehouse(Integer id, Map<String, Object> warehouseData) throws ResourceNotFoundException {
        return warehouseRepo.findById(id)
                .map((Warehouse foundWarehouse)->{
                    warehouseData.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Warehouse.class, key);

                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, foundWarehouse, value);
                        }
                    });
                    return warehouseRepo.save(foundWarehouse);
                })
                .orElseThrow(()->new ResourceNotFoundException("Warehouse Not Found"));
    }

    public void deleteWarehouse(Integer id) {
        warehouseRepo.deleteById(id);
    }

}
