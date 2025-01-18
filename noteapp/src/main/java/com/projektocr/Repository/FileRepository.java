package com.projektocr.Repository;

import com.projektocr.Model.FileProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileProcess, Long> {
}