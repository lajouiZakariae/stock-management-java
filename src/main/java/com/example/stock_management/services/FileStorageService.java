package com.example.stock_management.services;

import com.example.stock_management.exceptions.FileNotUploadedException;
import org.springframework.beans.factory.annotation.Value;
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

    private final Path uploadDir;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {

        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException ex) {
            throw new IOException("Could not create upload directory!", ex);
        }
    }

    /**
     * Stores the file and returns the stored file name.
     *
     * @param file the multipart file to store
     * @return the stored file name
     * @throws IOException if an I/O error occurs
     */
    public String storeFile(MultipartFile file) throws IOException, FileNotUploadedException {
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

        // Resolve the target location
        Path targetLocation = this.uploadDir.resolve(fileName);

        // Copy file to the target location (replacing existing file with the same name)
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    /**
     * Deletes the file with the given file name.
     *
     * @param fileName the name of the file to delete
     * @throws IOException if an I/O error occurs
     */
    public void deleteFile(String fileName) throws IOException {
        Path filePath = this.uploadDir.resolve(fileName).normalize();
        Files.deleteIfExists(filePath);
    }

    /**
     * Retrieves the file as a Path.
     *
     * @param fileName the name of the file to retrieve
     * @return the Path to the file
     */
    public Path getFilePath(String fileName) {
        return this.uploadDir.resolve(fileName).normalize();
    }
}
