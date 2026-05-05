package com.example.etudiants.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "etudiants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cin;

    @Column(nullable = false)
    private String nom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(unique = true)
    private String email;

    @Column(name = "annee_premiere_inscription")
    private int anneePremiereInscription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    /**
     * Q2 — Calcule dynamiquement l'âge de l'étudiant
     * @JsonIgnore évite que Jackson essaie de sérialiser cette méthode comme un champ
     */
    @com.fasterxml.jackson.annotation.JsonIgnore
    public int age() {
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }
}