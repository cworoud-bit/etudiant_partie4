package com.example.etudiants.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor      // ✅ Requis pour Redis deserialize
@AllArgsConstructor     // ✅ Requis par @Builder avec @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // ✅ Sécurité cache
public class EtudiantDTO implements Serializable {  // ✅ Requis pour Redis

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Le CIN est obligatoire")
    private String cin;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    @Email(message = "Email invalide")
    private String email;

    private int anneePremiereInscription;

    private Long departementId;
    private String departementNom;

    // Champ calculé
    private int age;
}