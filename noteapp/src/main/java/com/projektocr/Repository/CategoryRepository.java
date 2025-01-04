package com.projektocr.Repository;

import com.projektocr.Model.Category;
import com.projektocr.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
