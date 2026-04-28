package com.example.etudiants.controller;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.service.DepartementService;
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
@RequestMapping("/api/departements")
@RequiredArgsConstructor
@Tag(name = "Départements", description = "CRUD complet pour la gestion des départements")
public class DepartementController {

    private final DepartementService service;

    @GetMapping
    @Operation(summary = "Liste tous les départements")
    public ResponseEntity<List<DepartementDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un département par ID")
    @ApiResponse(responseCode = "404", description = "Département introuvable")
    public ResponseEntity<DepartementDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crée un département")
    @ApiResponse(responseCode = "201", description = "Département créé")
    public ResponseEntity<DepartementDTO> create(@Valid @RequestBody DepartementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un département")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody DepartementDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un département")
    @ApiResponse(responseCode = "204", description = "Département supprimé")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
