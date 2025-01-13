package com.projektocr.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Note {

    private static final String CATEGORY_ID_COLUMN = "category_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = CATEGORY_ID_COLUMN, nullable = false)
    private Category category;

    private String title;
    private String content;
    private String imageFilePath;

    @JsonProperty("category")
    public void deserializeCategory(String categoryName) {
        this.category = createCategory(categoryName);
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }
}