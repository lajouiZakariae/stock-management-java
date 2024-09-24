package com.example.stock_management.services;

import com.example.stock_management.config.StoragePropertiesConfig;
import com.example.stock_management.exceptions.InvalidFileOperationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final StoragePropertiesConfig storagePropertiesConfig;

    // Constructor that depends on the StoragePropertiesConfig
    public FileStorageService(StoragePropertiesConfig storagePropertiesConfig) {
        this.storagePropertiesConfig = storagePropertiesConfig;
    }

    public String storeFile(MultipartFile file) {
        return storeFileHandle(file, "public");
    }

    public String storeFile(MultipartFile file, String folderType) {
        return storeFileHandle(file, folderType);
    }

    public void deleteFile(String imagePath) {
        deleteFileHandle(imagePath, "public");
    }

    public void deleteFile(String imagePath, String folderType) {
        deleteFileHandle(imagePath, folderType);
    }

    private String storeFileHandle(MultipartFile file, String folderType) {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new InvalidFileOperationException("File is Empty");
        }

        // Normalize file name
        String originalFileNameNormalized = Paths.get(Objects.requireNonNull(file.getOriginalFilename())).getFileName().toString();
        String fileExtension = "";

        // Extract file extension
        int dotIndex = originalFileNameNormalized.lastIndexOf('.');
        if (dotIndex >= 0) {
            fileExtension = originalFileNameNormalized.substring(dotIndex);
        }

        // Generate a unique file name to prevent collisions
        String fileName = UUID.randomUUID() + fileExtension;

        // Check for invalid characters
        if (fileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence " + fileName);
        }

        // Get the directory path from StoragePropertiesConfig based on folderType
        String directoryPath = storagePropertiesConfig.getDirectoryPath(folderType);

        // Resolve the target location
        Path targetLocation = Paths.get(directoryPath).toAbsolutePath().normalize().resolve(fileName);

        try {
            // Ensure the directory exists
            Files.createDirectories(targetLocation.getParent());

            // Copy file to the target location (replacing existing file with the same name)
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Throwable e) {
            throw new InvalidFileOperationException(e.getMessage());
        }

        return fileName;
    }

    private void deleteFileHandle(String imagePath, String folderType) {
        Path targetDir = Paths.get(storagePropertiesConfig.getDirectoryPath(folderType));

        // Resolve the file path
        Path filePath = targetDir.resolve(imagePath).normalize();

        if (!Files.exists(filePath)) {
            throw new InvalidFileOperationException("File Not Found");
        }

        try {
            Files.delete(filePath);
        } catch (Throwable e) {
            throw new InvalidFileOperationException(e.getMessage());
        }

    }
}
