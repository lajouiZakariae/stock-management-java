package com.example.stock_management.services;

import com.example.stock_management.dtos.CreateWarehouseRequestDTO;
import com.example.stock_management.dtos.WarehouseFilter;
import com.example.stock_management.exceptions.FileNotUploadedException;
import com.example.stock_management.exceptions.ResourceNotFoundException;
import com.example.stock_management.models.Warehouse;
import com.example.stock_management.repositories.WarehouseRepository;
import com.example.stock_management.speceifications.WarehouseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

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

    public Warehouse getWarehouseById(Integer id) throws ResourceNotFoundException {
        return warehouseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));
    }

    public Warehouse addWarehouse(CreateWarehouseRequestDTO warehousePayload) throws IOException, FileNotUploadedException {
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

    public Warehouse updateWarehouse(Integer id, Map<String, Object> warehouseData) throws ResourceNotFoundException {
        return warehouseRepository.findById(id)
                .map((Warehouse foundWarehouse) -> {
                    warehouseData.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(Warehouse.class, key);

                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, foundWarehouse, value);
                        }

                    });
                    return warehouseRepository.save(foundWarehouse);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse Not Found"));
    }

    public void deleteWarehouse(Integer id) throws ResourceNotFoundException {
        Warehouse warehouse = warehouseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        //  fileStorageService.deleteFile(warehouse.getImagePath());

        warehouseRepository.delete(warehouse);
    }

}
