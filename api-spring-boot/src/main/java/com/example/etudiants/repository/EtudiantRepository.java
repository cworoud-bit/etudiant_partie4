package com.example.etudiants.repository;

import com.example.etudiants.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // Q9 — Filtre par année de première inscription
    List<Etudiant> findByAnneePremiereInscription(int annee);
}
