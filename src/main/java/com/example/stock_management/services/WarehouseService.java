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
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseService {

    @Autowired
    private  WarehouseRepo warehouseRepo;

    public List<Warehouse> getPaginatedWarehouses(@RequestParam Map<String,Object> params) {
//        Specification<Warehouse> spec = Specification
//                .where(WarehouseSpecification.buildSpecification(params));

        return  warehouseRepo.findAll();
    }
//
//    public Page<Warehouse> getAllWarehouses(Map<String,String> filter,Integer page) {
//        Specification<Warehouse> spec = Specification
//                .where(WarehouseSpecification.hasName(filter.get("name")));
//
//        return  warehouseRepo.findAll(spec,PageRequest.of(page,10));
//    }

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
                .orElseThrow(()->new NotFoundException("Warehouse Not Found"));
    }

    public void deleteWarehouse(Integer id) {
        warehouseRepo.deleteById(id);
    }

}
