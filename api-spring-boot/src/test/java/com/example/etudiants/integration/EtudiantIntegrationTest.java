package com.example.etudiants.integration;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.repository.EtudiantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EtudiantIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Test
    void shouldCreateAndRetrieveEtudiant() {
        // Create
        EtudiantDTO newEtudiant = new EtudiantDTO();
        newEtudiant.setNom("Test Integration");
        newEtudiant.setPrenom("Test");
        newEtudiant.setCin("12345678");
        newEtudiant.setDateNaissance(LocalDate.of(1995, 5, 15));
        newEtudiant.setAnneePremiereInscription(2024);

        ResponseEntity<EtudiantDTO> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/etudiants",
                newEtudiant,
                EtudiantDTO.class
        );

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getId()).isNotNull();

        // Retrieve
        Long id = createResponse.getBody().getId();
        ResponseEntity<EtudiantDTO> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/etudiants/" + id,
                EtudiantDTO.class
        );

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getNom()).isEqualTo("Test Integration");
    }

    @Test
    void shouldFindAllEtudiants() {
        // Given
        Etudiant e = new Etudiant();
        e. Nom("Dupont");
        e.setCin("11111111");
        e.setEmail("dupont@test.com");
        e.setDateNaissance(LocalDate.of(2000, 1, 1));
        e.setAnneePremiereInscription(2023);
        etudiantRepository.save(e);

        // When
        ResponseEntity<EtudiantDTO[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/etudiants",
                EtudiantDTO[].class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }
}