package com.example.etudiants.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EtudiantDTO {

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
