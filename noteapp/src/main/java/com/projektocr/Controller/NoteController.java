package com.projektocr.Controller;

import com.projektocr.Model.Note;
import com.projektocr.Service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final String uploadDir = "uploads/"; // Ścieżka do folderu z przesyłanymi plikami (można przenieść do application.properties)

    // Konstruktor przyjmujący usługę NoteService
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Pobierz wszystkie notatki
    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    // Pobierz pojedynczą notatkę po ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        // Sprawdzamy, czy notatka o danym ID istnieje
        Optional<Note> note = noteService.getNoteById(id);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Utwórz nową notatkę
    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        // Tworzymy notatkę i zapisujemy ją w bazie danych
        Note createdNote = noteService.saveNote(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED); // Zwracamy status 201 Created
    }

    // Przesyłanie obrazu związane z notatką
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        // Sprawdzamy, czy plik nie jest pusty
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Nie przesłano pliku.");
        }

        // Tworzymy katalog do przesyłania plików, jeśli nie istnieje
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Nie udało się stworzyć katalogu na pliki.");
        }

        // Definiujemy ścieżkę do pliku
        String filePath = uploadDir + file.getOriginalFilename();

        // Zapisujemy plik na dysku
        try {
            file.transferTo(new File(filePath));
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Plik został pomyślnie przesłany: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Nie udało się przesłać pliku: " + e.getMessage());
        }
    }

    // Usuwanie notatki po ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        // Usuwamy notatkę o danym ID
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build(); // Zwracamy status 204 No Content
    }
}
