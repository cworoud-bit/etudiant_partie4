package com.example.etudiants.bdd;

import com.example.etudiants.entity.Etudiant;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Étantdonné;
import io.cucumber.java.fr.Quand;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtudiantSteps {

    private Etudiant etudiant;
    private int ageCalcule;

    @Étantdonné("un étudiant avec la date de naissance {string}")
    public void unEtudiantAvecLaDateDeNaissance(String dateStr) {
        etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.parse(dateStr));
    }

    @Quand("on calcule son âge")
    public void onCalculeSonAge() {
        ageCalcule = etudiant.age();
    }

    @Alors("l'âge retourné doit être {int}")
    public void lAgeRetournéDoitEtre(int ageAttendu) {
        assertEquals(ageAttendu, ageCalcule,
                "L'âge calculé (" + ageCalcule + ") ne correspond pas à l'âge attendu (" + ageAttendu + ")");
    }
}
