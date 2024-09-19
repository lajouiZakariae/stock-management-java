package com.example.stock_management.speceifications;

import com.example.stock_management.models.Warehouse;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class WarehouseSpecification {
    public static Specification<Warehouse> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null) {
                // Use criteriaBuilder.like with the direct reference to the root attribute "name"
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
            return criteriaBuilder.conjunction(); // Return no filtering if title is null
        };
    }

    public static Specification<Warehouse> buildSpecification(String name) {
        return Specification.where(
                hasName(name)
        );
    }
}
