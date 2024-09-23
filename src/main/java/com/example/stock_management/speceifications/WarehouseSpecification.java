package com.example.stock_management.speceifications;

import com.example.stock_management.models.Warehouse;
import org.springframework.data.jpa.domain.Specification;

public class WarehouseSpecification {

    public static Specification<Warehouse> hasNameLike(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Warehouse> hasLocationLike(String location) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }

}
