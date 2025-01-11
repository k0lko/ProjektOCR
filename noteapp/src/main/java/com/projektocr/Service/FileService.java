package com.projektocr.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    private final String uploadDir;

    public FileService(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Plik jest pusty.");
        }

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.startsWith("image/") && !contentType.startsWith("application/"))) {
            throw new IllegalArgumentException("Nieprawidłowy typ pliku. Dozwolone są tylko obrazy i dokumenty.");
        }

        long maxFileSize = 5 * 1024 * 1024; // 5 MB
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("Plik jest zbyt duży. Maksymalny rozmiar to 5 MB.");
        }

        Files.createDirectories(Paths.get(uploadDir));
        String filePath = uploadDir + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return filePath;
    }
}
