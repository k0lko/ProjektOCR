package com.projektocr.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tytuł jest wymagany.")
    @Size(max = 100, message = "Tytuł nie może przekraczać 100 znaków.")
    private String title;

    @Size(max = 255, message = "Opis nie może przekraczać 255 znaków.")
    private String description;

    private String category;

    public Note() {}

    public Note(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    // Gettery i Settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
