package com.example.stock_management.services;

import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repo.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private  WarehouseRepo warehouseRepo;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepo.findAll();
    }

    public Warehouse getWarehouseById(int id)  {
        return warehouseRepo.findById(id).orElseThrow();
    }

    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public Warehouse updateWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public void deleteWarehouse(int id) {
        warehouseRepo.deleteById(id);
    }

}
