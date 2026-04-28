package com.example.grading.controller;

import com.example.grading.dto.NoteDTO;
import com.example.grading.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "CRUD complet pour la gestion des notes")
public class NoteController {

    private final NoteService service;

    @GetMapping
    @Operation(summary = "Liste toutes les notes (ou par étudiant avec ?studentId=X)")
    public ResponseEntity<List<NoteDTO>> getAll(
            @RequestParam(required = false) Long studentId) {
        if (studentId != null) {
            return ResponseEntity.ok(service.findByStudentId(studentId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère une note par ID")
    @ApiResponse(responseCode = "200", description = "Note trouvée")
    @ApiResponse(responseCode = "404", description = "Note introuvable")
    public ResponseEntity<NoteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crée une note — vérifie l'existence de l'étudiant via Feign")
    @ApiResponse(responseCode = "201", description = "Note créée")
    @ApiResponse(responseCode = "400", description = "Données invalides ou étudiant inexistant")
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour une note")
    @ApiResponse(responseCode = "200", description = "Note mise à jour")
    @ApiResponse(responseCode = "404", description = "Note introuvable")
    public ResponseEntity<NoteDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody NoteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime une note")
    @ApiResponse(responseCode = "204", description = "Note supprimée")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
