//package com.projektocr.Model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//@Entity
//@Table(name = "users")
////@NoArgsConstructor // Generates a no-argument constructor (useful for serialization)
////@AllArgsConstructor // Generates a constructor with all fields
////@Getter
////@Setter
//@EqualsAndHashCode
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generate ID
//    private Long id;
//
//    @Column(unique = true, nullable = false) // Unique and required username
//    private String username;
//
//    @Column(nullable = false) // Required password
//    private String password;
//
//    private String role = "USER"; // Default role for user
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Note> notes;
//
//    public User() {
//    }
//
//    // Constructor with basic fields (useful for registration)
//    public User(String username, String password, String role) {
//        this.username = username;
//        this.password = password;
//        this.role = role;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public List<Note> getNotes() {
//        return notes;
//    }
//
//    public void setNotes(List<Note> notes) {
//        this.notes = notes;
//    }
//}