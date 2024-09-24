package com.example.stock_management.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdateWarehouseRequestDto {

    private MultipartFile image;

    @NotEmpty(message = "Warehouse name is required")
    @Size(max = 255, message = "Warehouse name must be less than 255 characters")
    private String name;

    @Min(value = -90, message = "Latitude must be greater than or equal to -90")
    @Max(value = 90, message = "Latitude must be less than or equal to 90")
    private Double latitude;

    @Min(value = -180, message = "Longitude must be greater than or equal to -180")
    @Max(value = 180, message = "Longitude must be less than or equal to 180")
    private Double longitude;

    @Min(value = 1, message = "Minimum capacity must be greater than 0")
    private Integer minCapacity;

    @Min(value = 1, message = "Maximum capacity must be greater than 0")
    private Integer maxCapacity;

}
