package com.example.stock_management.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateWarehouseRequestDTO {

    @NotNull(message = "Image file is required")
    private MultipartFile image;

    @NotEmpty(message = "Warehouse name is required")
    @Size(max = 255, message = "Warehouse name must be less than 255 characters")
    private String name;

    @NotNull(message = "Latitude is required")
    @Min(value = -90, message = "Latitude must be greater than or equal to -90")
    @Max(value = 90, message = "Latitude must be less than or equal to 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Min(value = -180, message = "Longitude must be greater than or equal to -180")
    @Max(value = 180, message = "Longitude must be less than or equal to 180")
    private Double longitude;

    @NotNull(message = "Minimum capacity is required")
    @Min(value = 1, message = "Minimum capacity must be greater than 0")
    private Integer minCapacity;

    @NotNull(message = "Maximum capacity is required")
    @Min(value = 1, message = "Maximum capacity must be greater than 0")
    private Integer maxCapacity;

}
