package com.example.stock_management.filters;

import javax.validation.constraints.Size;

public class WarehouseFilter {

    @Size(min = 3)
    private String name;

}
