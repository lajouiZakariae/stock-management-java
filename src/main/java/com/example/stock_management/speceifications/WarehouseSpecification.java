package com.example.stock_management.speceifications;

import com.example.stock_management.filters.WarehouseFilter;
import com.example.stock_management.models.Warehouse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseSpecification {

    public static Specification<Warehouse> hasNameLike(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

}
