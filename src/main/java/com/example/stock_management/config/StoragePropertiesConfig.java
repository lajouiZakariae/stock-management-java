package com.example.stock_management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoragePropertiesConfig {

    private final String localUploadDir;
    private final String publicUploadDir;

    // Constructor for directory paths from application.properties
    public StoragePropertiesConfig(@Value("${file.local-upload-dir}") String localUploadDir,
                                   @Value("${file.public-upload-dir}") String publicUploadDir) {
        this.localUploadDir = "storage/" + localUploadDir;
        this.publicUploadDir = "storage/" + publicUploadDir;
    }

    /**
     * Get the directory path based on folder type.
     *
     * @param folderType either "local" or "public"
     * @return the directory path
     * @throws IllegalArgumentException if an invalid folder type is provided
     */
    public String getDirectoryPath(String folderType) {
        return switch (folderType.toLowerCase()) {
            case "local" -> localUploadDir;
            case "public" -> publicUploadDir;
            default -> throw new IllegalArgumentException("Invalid folder type: " + folderType);
        };
    }
}
