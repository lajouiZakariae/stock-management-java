package com.example.stock_management.filters;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class WarehouseFilter {

    @Min(3)
    @Max(7)
    @NotBlank(message = "The name is required.")
    private String name;


}
