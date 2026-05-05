package com.example.etudiants.service;

import com.example.etudiants.config.ResourceNotFoundException;
import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.mapper.EtudiantMapper;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private EtudiantMapper mapper;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant etudiant;
    private EtudiantDTO etudiantDTO;
    private Departement departement;

    @BeforeEach
    void setUp() {
        departement = Departement.builder()
                .id(1L)
                .nom("Informatique")
                .build();

        etudiant = Etudiant.builder()
                .id(1L)
                .cin("12345678")
                .nom("Ben Ali Ahmed")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .email("ahmed@example.com")
                .anneePremiereInscription(2023)
                .departement(departement)
                .build();

        etudiantDTO = EtudiantDTO.builder()
                .id(1L)
                .cin("12345678")
                .nom("Ben Ali Ahmed")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .email("ahmed@example.com")
                .anneePremiereInscription(2023)
                .departementId(1L)
                .build();
    }

    @Test
    void shouldReturnAllEtudiants() {
        // Given
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant));
        when(mapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        // When
        List<EtudiantDTO> result = etudiantService.findAll();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Ben Ali Ahmed");
        assertThat(result.get(0).getCin()).isEqualTo("12345678");

        verify(etudiantRepository, times(1)).findAll();
        verify(mapper, times(1)).toDTO(etudiant);
    }

    @Test
    void shouldReturnEmptyListWhenNoEtudiants() {
        // Given
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<EtudiantDTO> result = etudiantService.findAll();

        // Then
        assertThat(result).isEmpty();
        verify(etudiantRepository, times(1)).findAll();
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void shouldFindEtudiantById() {
        // Given
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(mapper.toDTO(etudiant)).thenReturn(etudiantDTO);

        // When
        EtudiantDTO result = etudiantService.findById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNom()).isEqualTo("Ben Ali Ahmed");

        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenEtudiantNotFound() {
        // Given
        when(etudiantRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> etudiantService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Étudiant introuvable avec id=999");

        verify(etudiantRepository, times(1)).findById(999L);
    }

    @Test
    void shouldFindByAnnee() {
        // Given
        int annee = 2023;
        when(etudiantRepository.findByAnneePremiereInscription(annee))
                .thenReturn(Arrays.asList(etudiant));
        when(mapper.toDTO(any(Etudiant.class))).thenReturn(etudiantDTO);

        // When
        List<EtudiantDTO> result = etudiantService.findByAnnee(annee);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAnneePremiereInscription()).isEqualTo(2023);

        verify(etudiantRepository, times(1))
                .findByAnneePremiereInscription(annee);
    }

    @Test
    void shouldSaveEtudiantWithDepartement() {
        // Given
        when(mapper.toEntity(etudiantDTO)).thenReturn(etudiant);
        when(departementRepository.findById(1L)).thenReturn(Optional.of(departement));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(mapper.toDTO(etudiant)).thenReturn(etudiantDTO);

        // When
        EtudiantDTO result = etudiantService.save(etudiantDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(departementRepository, times(1)).findById(1L);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void shouldThrowExceptionWhenSaveWithInvalidDepartement() {
        // Given
        when(mapper.toEntity(etudiantDTO)).thenReturn(etudiant);
        when(departementRepository.findById(999L)).thenReturn(Optional.empty());
        etudiantDTO.setDepartementId(999L);

        // When & Then
        assertThatThrownBy(() -> etudiantService.save(etudiantDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Département introuvable");

        verify(etudiantRepository, never()).save(any());
    }

    @Test
    void shouldUpdateEtudiant() {
        // Given
        EtudiantDTO updateDTO = EtudiantDTO.builder()
                .cin("87654321")
                .nom("Ben Ali Mohamed")
                .dateNaissance(LocalDate.of(2001, 2, 2))
                .email("mohamed@example.com")
                .anneePremiereInscription(2024)
                .departementId(1L)
                .build();

        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1L)).thenReturn(Optional.of(departement));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(mapper.toDTO(etudiant)).thenReturn(updateDTO);

        // When
        EtudiantDTO result = etudiantService.update(1L, updateDTO);

        // Then
        assertThat(result).isNotNull();
        verify(etudiantRepository, times(1)).findById(1L);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void shouldThrowExceptionWhenUpdateNonExistentEtudiant() {
        // Given
        when(etudiantRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> etudiantService.update(999L, etudiantDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Étudiant introuvable avec id=999");

        verify(etudiantRepository, never()).save(any());
    }

    @Test
    void shouldDeleteEtudiant() {
        // Given
        when(etudiantRepository.existsById(1L)).thenReturn(true);
        doNothing().when(etudiantRepository).deleteById(1L);

        // When
        etudiantService.delete(1L);

        // Then
        verify(etudiantRepository, times(1)).existsById(1L);
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeleteNonExistentEtudiant() {
        // Given
        when(etudiantRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> etudiantService.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Étudiant introuvable avec id=999");

        verify(etudiantRepository, never()).deleteById(any());
    }
}