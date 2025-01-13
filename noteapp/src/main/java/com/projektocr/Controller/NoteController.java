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
    private static final String UPLOAD_DIRECTORY = "uploads/"; // Przeniesiono do stałej
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteService.getNoteById(id);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        Note createdNote = noteService.saveNote(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nie przesłano pliku.");
        }
        try {
            createUploadDirectoryIfNotExists();
            String filePath = saveFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("Plik został pomyślnie przesłany: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nie udało się przesłać pliku: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    // Ekstrakcja metody do tworzenia katalogu
    private void createUploadDirectoryIfNotExists() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
    }

    // Ekstrakcja metody do zapisu plików
    private String saveFile(MultipartFile file) throws IOException {
        String filePath = UPLOAD_DIRECTORY + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return filePath;
    }
}