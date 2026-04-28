package com.example.etudiants.controller;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.service.EtudiantService;
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
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
@Tag(name = "Étudiants", description = "CRUD complet pour la gestion des étudiants")
public class EtudiantController {

    private final EtudiantService service;

    @GetMapping
    @Operation(summary = "Liste tous les étudiants", description = "Supporte le filtre par ?annee=XXXX")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    public ResponseEntity<List<EtudiantDTO>> getAll(
            @RequestParam(required = false) Integer annee) {
        if (annee != null) {
            return ResponseEntity.ok(service.findByAnnee(annee));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un étudiant par ID")
    @ApiResponse(responseCode = "200", description = "Étudiant trouvé")
    @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    public ResponseEntity<EtudiantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crée un nouvel étudiant")
    @ApiResponse(responseCode = "201", description = "Étudiant créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<EtudiantDTO> create(@Valid @RequestBody EtudiantDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un étudiant")
    @ApiResponse(responseCode = "200", description = "Étudiant mis à jour")
    @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    public ResponseEntity<EtudiantDTO> update(@PathVariable Long id,
                                               @Valid @RequestBody EtudiantDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un étudiant")
    @ApiResponse(responseCode = "204", description = "Étudiant supprimé")
    @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
