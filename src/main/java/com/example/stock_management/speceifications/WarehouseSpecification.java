package com.example.stock_management.speceifications;

import com.example.stock_management.models.Warehouse;
import org.springframework.data.jpa.domain.Specification;

public class WarehouseSpecification {
    public  static Specification<Warehouse> hasName(String name){
        return  (root,query,criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }
}
