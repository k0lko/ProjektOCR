package com.projektocr.Repository;

import com.projektocr.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByCategory(String category);

    List<Note> findByTitle(String title);

    List<Note> findByCategoryAndTitle(String category, String title);


    List<Note> findByTitleContaining(String title);

    Optional<Note> findById(Long id);

    Optional<Note> findFirstByCategory(String category);
}
