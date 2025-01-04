package com.projektocr.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: USUNAC KOMENTARZE

//    private Long category_id;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String title;
    private String content;
    private String imagePath;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //TODO: USUSNAC KOMENTARZE

//    @OneToOne
//    @JoinColumn
//    public Category getCategory() {
//        return category;
//    }

    @JsonProperty("category")
    public void deserializeCategory(String category) {
        Category categoryObj = new Category();
        categoryObj.setName(category);
        this.category = categoryObj;
    }

}
