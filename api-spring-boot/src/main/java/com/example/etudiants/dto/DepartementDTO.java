package com.example.etudiants.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartementDTO {

    private Long id;

    @NotBlank(message = "Le nom du département est obligatoire")
    private String nom;
}
