package com.example.etudiants;

import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class EtudiantsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtudiantsApiApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(EtudiantRepository etudiantRepo, DepartementRepository depRepo) {
        return args -> {
            if (depRepo.count() > 0) return; // évite les doublons au redémarrage

            Departement info = depRepo.save(Departement.builder().nom("Informatique").build());
            Departement gestion = depRepo.save(Departement.builder().nom("Gestion").build());
            Departement maths = depRepo.save(Departement.builder().nom("Mathématiques").build());

            etudiantRepo.save(Etudiant.builder()
                    .cin("12345678").nom("Ali Ben Salah")
                    .dateNaissance(LocalDate.of(2001, 3, 15))
                    .email("ali@example.com").anneePremiereInscription(2019)
                    .departement(info).build());

            etudiantRepo.save(Etudiant.builder()
                    .cin("23456789").nom("Fatma Trabelsi")
                    .dateNaissance(LocalDate.of(2002, 7, 22))
                    .email("fatma@example.com").anneePremiereInscription(2020)
                    .departement(gestion).build());

            etudiantRepo.save(Etudiant.builder()
                    .cin("34567890").nom("Mohamed Khelil")
                    .dateNaissance(LocalDate.of(2000, 11, 8))
                    .email("mohamed@example.com").anneePremiereInscription(2019)
                    .departement(maths).build());

            etudiantRepo.save(Etudiant.builder()
                    .cin("45678901").nom("Rim Gharbi")
                    .dateNaissance(LocalDate.of(2003, 1, 30))
                    .email("rim@example.com").anneePremiereInscription(2021)
                    .departement(info).build());

            etudiantRepo.save(Etudiant.builder()
                    .cin("56789012").nom("Youssef Mansouri")
                    .dateNaissance(LocalDate.of(2001, 9, 5))
                    .email("youssef@example.com").anneePremiereInscription(2020)
                    .departement(gestion).build());

            System.out.println("✅ Données initiales chargées (3 départements, 5 étudiants).");
        };
    }
}
