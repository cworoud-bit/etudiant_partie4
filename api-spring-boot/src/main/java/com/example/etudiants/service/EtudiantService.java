package com.example.etudiants.service;

import com.example.etudiants.config.ResourceNotFoundException;
import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.mapper.EtudiantMapper;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;
    private final EtudiantMapper mapper;

    // Q13 — Cache Redis sur la liste
    @Cacheable(value = "etudiants")
    public List<EtudiantDTO> findAll() {
        return etudiantRepository.findAll()
                .stream().map(mapper::toDTO).toList();
    }

    // Q9 — Filtre par année
    public List<EtudiantDTO> findByAnnee(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee)
                .stream().map(mapper::toDTO).toList();
    }

    public EtudiantDTO findById(Long id) {
        Etudiant e = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable avec id=" + id));
        return mapper.toDTO(e);
    }

    // Q13 — Invalide le cache à la création
    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO save(EtudiantDTO dto) {
        Etudiant etudiant = mapper.toEntity(dto);
        if (dto.getDepartementId() != null) {
            Departement dep = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new ResourceNotFoundException("Département introuvable"));
            etudiant.setDepartement(dep);
        }
        return mapper.toDTO(etudiantRepository.save(etudiant));
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO update(Long id, EtudiantDTO dto) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable avec id=" + id));
        existing.setCin(dto.getCin());
        existing.setNom(dto.getNom());
        existing.setDateNaissance(dto.getDateNaissance());
        existing.setEmail(dto.getEmail());
        existing.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        if (dto.getDepartementId() != null) {
            Departement dep = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new ResourceNotFoundException("Département introuvable"));
            existing.setDepartement(dep);
        }
        return mapper.toDTO(etudiantRepository.save(existing));
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public void delete(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Étudiant introuvable avec id=" + id);
        }
        etudiantRepository.deleteById(id);
    }
}
