package com.example.stock_management.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class WarehouseFilter {

    @Size(max = 255)
    private String name;

}
