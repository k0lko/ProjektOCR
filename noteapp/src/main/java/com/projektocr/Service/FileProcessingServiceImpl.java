package com.projektocr.Service;

import com.projektocr.Model.FileProcess;
import com.projektocr.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileProcessingServiceImpl implements FileProcessingService {

    private final FileRepository fileRepository;

    @Autowired
    public FileProcessingServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void processFile(FileProcess fileProcess) {
        fileRepository.save(fileProcess);
    }

    @Override
    public List<FileProcess> getAllProcessedFiles() {
        return fileRepository.findAll();
    }
}