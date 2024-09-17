package com.example.stock_management.services;

import com.example.stock_management.exceptions.NotFoundException;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repo.WarehouseRepo;
import com.example.stock_management.speceifications.WarehouseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseService {

    @Autowired
    private  WarehouseRepo warehouseRepo;

    public Page<Warehouse> getPaginatedWarehouses(int page, int size) {
        return  warehouseRepo.findAll(PageRequest.of(page, size));
    }

    public List<Warehouse> getAllWarehouses(String name) {
        Specification<Warehouse> spec = Specification
                .where(WarehouseSpecification.hasName(name));

        return warehouseRepo.findAll(spec);
    }

    public Warehouse getWarehouseById(Integer id)  {
        return warehouseRepo
                .findById(id)
                .orElseThrow(()-> new NotFoundException("Warehouse not found"));
    }

    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public Warehouse updateWarehouse(Integer id, Map<String, Object> warehouseData) {
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
                .orElseThrow(()->new NotFoundException("Warehouse"));
    }

    public void deleteWarehouse(Integer id) {
        warehouseRepo.deleteById(id);
    }

}
