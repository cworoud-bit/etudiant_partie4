package com.example.etudiants.service;

import com.example.etudiants.config.ResourceNotFoundException;
import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.mapper.DepartementMapper;
import com.example.etudiants.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartementService {

    private final DepartementRepository departementRepository;
    private final DepartementMapper mapper;

    public List<DepartementDTO> findAll() {
        return departementRepository.findAll()
                .stream().map(mapper::toDTO).toList();
    }

    public DepartementDTO findById(Long id) {
        return departementRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec id=" + id));
    }

    public DepartementDTO save(DepartementDTO dto) {
        return mapper.toDTO(departementRepository.save(mapper.toEntity(dto)));
    }

    public DepartementDTO update(Long id, DepartementDTO dto) {
        departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec id=" + id));
        dto.setId(id);
        return mapper.toDTO(departementRepository.save(mapper.toEntity(dto)));
    }

    public void delete(Long id) {
        if (!departementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Département introuvable avec id=" + id);
        }
        departementRepository.deleteById(id);
    }
}
