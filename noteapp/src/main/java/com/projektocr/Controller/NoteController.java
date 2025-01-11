package com.projektocr.Controller;

import com.projektocr.Model.Note;
import com.projektocr.Service.NoteService;
import com.projektocr.Service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final FileService fileService;

    @Value("${upload.dir}")
    private String uploadDir;

    public NoteController(NoteService noteService, FileService fileService) {
        this.noteService = noteService;
        this.fileService = fileService;
    }

    // Pobierz wszystkie notatki
    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    // Pobierz pojedynczą notatkę po ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteService.getNoteById(id);
        return note.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Utwórz nową notatkę
    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        Note createdNote = noteService.saveNote(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    // Przesyłanie pliku związanego z notatką
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Plik został pomyślnie przesłany: " + filePath);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Wystąpił błąd podczas przesyłania pliku: " + e.getMessage());
        }
    }

    // Usuwanie notatki po ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        if (!noteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }
}
