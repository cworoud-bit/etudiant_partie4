package com.example.grading.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private Long id;

    @NotNull(message = "L'ID étudiant est obligatoire")
    private Long studentId;

    @NotBlank(message = "La matière est obligatoire")
    private String matiere;

    @NotNull(message = "La valeur est obligatoire")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "20.0")
    private Double valeur;
}
