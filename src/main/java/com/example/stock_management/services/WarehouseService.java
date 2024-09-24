package com.example.stock_management.services;

import com.example.stock_management.dtos.CreateWarehouseRequestDTO;
import com.example.stock_management.dtos.UpdateWarehouseRequestDto;
import com.example.stock_management.dtos.WarehouseFilter;
import com.example.stock_management.exceptions.ResourceNotFoundException;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repositories.WarehouseRepository;
import com.example.stock_management.speceifications.WarehouseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    private final FileStorageService fileStorageService;

    public Page<Warehouse> getPaginatedFilteredWarehouses(WarehouseFilter warehouseFilter, int page, int perPage) {
        Specification<Warehouse> warehouseSpecification = Specification.where(null);

        if (StringUtils.hasText(warehouseFilter.getName())) {
            warehouseSpecification = warehouseSpecification.and(WarehouseSpecification.hasNameLike(warehouseFilter.getName()));
        }

        return warehouseRepository.findAll(warehouseSpecification, PageRequest.of(page, perPage));
    }

    public Warehouse getWarehouseById(Integer id) {
        return warehouseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));
    }

    public Warehouse addWarehouse(CreateWarehouseRequestDTO warehousePayload) {
        // Build The Warehouse
        var warehouse = Warehouse.builder()
                .name(warehousePayload.getName())
                .minCapacity(warehousePayload.getMinCapacity())
                .maxCapacity(warehousePayload.getMaxCapacity())
                .longitude(warehousePayload.getLongitude())
                .latitude(warehousePayload.getLatitude())
                .build();

        // Save The Image
        MultipartFile imageFile = warehousePayload.getImage();

        warehouse.setImagePath(fileStorageService.storeFile(imageFile));

        // Persist to the database
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(Integer id, UpdateWarehouseRequestDto warehousePayload) {

        Warehouse warehouse = warehouseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse Not Found"));

        if (!warehousePayload.getImage().isEmpty()) {
            if (!warehouse.getImagePath().isEmpty()) {
                fileStorageService.deleteFile(warehouse.getImagePath());
            }

            warehouse.setImagePath(fileStorageService.storeFile(warehousePayload.getImage()));
        }

        // Update other fields if they are not null
        if (warehousePayload.getName() != null && !warehousePayload.getName().isEmpty()) {
            warehouse.setName(warehousePayload.getName());
        }

        if (warehousePayload.getLatitude() != null) {
            warehouse.setLatitude(warehousePayload.getLatitude());
        }

        if (warehousePayload.getLongitude() != null) {
            warehouse.setLongitude(warehousePayload.getLongitude());
        }

        if (warehousePayload.getMinCapacity() != null) {
            warehouse.setMinCapacity(warehousePayload.getMinCapacity());
        }

        if (warehousePayload.getMaxCapacity() != null) {
            warehouse.setMaxCapacity(warehousePayload.getMaxCapacity());
        }

        // Save the updated warehouse
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Integer id) {
        Warehouse warehouse = warehouseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        fileStorageService.deleteFile(warehouse.getImagePath());

        warehouseRepository.delete(warehouse);
    }

}
