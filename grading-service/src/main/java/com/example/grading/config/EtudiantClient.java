package com.example.grading.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client Feign pour communiquer avec etudiant-service via Eureka.
 * Vérifie l'existence d'un étudiant avant d'enregistrer une note.
 */
@FeignClient(name = "etudiant-service")
public interface EtudiantClient {

    @GetMapping("/api/etudiants/{id}")
    Object getEtudiantById(@PathVariable("id") Long id);
}
