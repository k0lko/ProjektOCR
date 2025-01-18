package com.projektocr.Service;

import com.projektocr.Model.FileProcess;

import java.util.List;

public interface FileProcessingService {
    void processFile(FileProcess fileProcess);
    List<FileProcess> getAllProcessedFiles();
}