package com.projektocr.Controller;

import com.projektocr.Model.FileProcess;
import com.projektocr.Service.FileProcessingService;
import com.projektocr.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final FileProcessingService fileProcessingService;

    @Autowired
    public FileController(FileService fileService, FileProcessingService fileProcessingService) {
        this.fileService = fileService;
        this.fileProcessingService = fileProcessingService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("title") String title,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("category") String category,
                                                   @RequestParam("file") MultipartFile file) {
        try {
            String uniqueFileName = fileService.saveFile(file);
            FileProcess fileProcess = new FileProcess();
            fileProcess.setNazwa(title);
            fileProcess.setOpis(description);
            fileProcess.setKategoria(category);
            fileProcess.setNazwaPliku(uniqueFileName);
            fileProcessingService.processFile(fileProcess);
            return ResponseEntity.ok("Plik przesłany i przetworzony!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Błąd przesyłania pliku!");
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.loadFileAsResource(fileName);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        String contentType = "application/octet-stream"; // Domyślny typ MIME
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return ResponseEntity.ok("Plik usunięty!");
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileProcess>> getFileList(){
        List<FileProcess> fileList = fileProcessingService.getAllProcessedFiles();
        return ResponseEntity.ok(fileList);
    }
}