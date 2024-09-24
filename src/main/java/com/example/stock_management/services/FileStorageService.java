package com.example.stock_management.services;

import com.example.stock_management.config.StoragePropertiesConfig;
import com.example.stock_management.exceptions.FileNotUploadedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public FileStorageService(StoragePropertiesConfig storagePropertiesConfig)  {
        this.storagePropertiesConfig = storagePropertiesConfig;
    }

    /**
     * Stores the file in the appropriate folder (local or public) based on the folder type.
     *
     * @param file       the multipart file to store
     * @return the stored file name
     * @throws IOException if an I/O error occurs
     */
    public String storeFile(MultipartFile file) throws IOException, FileNotUploadedException {
        return storeFileHandle(file, "public");
    }

    /**
     * Stores the file in the appropriate folder (local or public) based on the folder type.
     *
     * @param file       the multipart file to store
     * @param folderType the folder type ("local" or "public")
     * @return the stored file name
     * @throws IOException if an I/O error occurs
     */
    public String storeFile(MultipartFile file, String folderType) throws IOException, FileNotUploadedException {
        return storeFileHandle(file, folderType);
    }

    /**
     * Stores the file in the appropriate folder (local or public) based on the folder type.
     *
     * @param file       the multipart file to store
     * @param folderType the folder type ("local" or "public")
     * @return the stored file name
     * @throws IOException if an I/O error occurs
     */
    private String storeFileHandle(MultipartFile file, String folderType) throws IOException, FileNotUploadedException {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new FileNotUploadedException("File is Empty");
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
            throw new IOException("Filename contains invalid path sequence " + fileName);
        }

        // Get the directory path from StoragePropertiesConfig based on folderType
        String directoryPath = storagePropertiesConfig.getDirectoryPath(folderType);

        // Resolve the target location
        Path targetLocation = Paths.get(directoryPath).toAbsolutePath().normalize().resolve(fileName);

        // Ensure the directory exists
        Files.createDirectories(targetLocation.getParent());

        // Copy file to the target location (replacing existing file with the same name)
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

}
