package com.projektocr.Controller;

import com.projektocr.Model.FileProcess;
import com.projektocr.Service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileProcessingController {

    private final FileProcessingService fileProcessingService; // Poprawna nazwa

    @Autowired
    public FileProcessingController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @GetMapping("/processed")
    public ResponseEntity<List<FileProcess>> getProcessedFiles() {
        List<FileProcess> processedFiles = fileProcessingService.getAllProcessedFiles();
        return ResponseEntity.ok(processedFiles);
    }
}