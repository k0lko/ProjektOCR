package com.projektocr.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files/upload")
public class FileController {

    private final String uploadDir;

    public FileController() {
        // Pobieranie katalogu roboczego aplikacji
        String projectDir = System.getProperty("user.dir");

        // Ścieżka do folderu, gdzie będą przechowywane pliki
        this.uploadDir = projectDir + "/uploads";  // Możesz zmienić nazwę folderu, np. "/files"
    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("title") String title,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("category") String category,
                                                   @RequestParam("file") MultipartFile file) {
        try {
            // Sprawdzenie, czy plik jest pusty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Plik jest wymagany!");
            }

            // Sprawdzenie i stworzenie katalogu, jeśli nie istnieje
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Zapis pliku na serwerze
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());

            return ResponseEntity.ok("Dokument został dodany!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Błąd serwera podczas zapisywania pliku!");
        }
    }
}
