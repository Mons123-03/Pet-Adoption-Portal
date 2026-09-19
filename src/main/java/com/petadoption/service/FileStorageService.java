package com.petadoption.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload-dir}")
    private String uploadDir;

    /**
     * Saves an uploaded file under the configured upload directory with a
     * random unique name (to avoid collisions) and returns the public URL
     * path to store in the database, e.g. "/uploads/ab12-cd34.jpg".
     */
    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf('.'));
            }

            String newFileName = UUID.randomUUID().toString() + extension;
            Path targetPath = Path.of(uploadDir, newFileName);
            Files.copy(file.getInputStream(), targetPath);

            return "/uploads/" + newFileName;
        } catch (IOException e) {
            throw new ApiException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to save uploaded image");
        }
    }
}
