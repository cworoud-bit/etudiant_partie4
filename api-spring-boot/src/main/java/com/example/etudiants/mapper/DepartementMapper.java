package com.example.etudiants.mapper;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementDTO toDTO(Departement d) {
        return DepartementDTO.builder()
                .id(d.getId())
                .nom(d.getNom())
                .build();
    }

    public Departement toEntity(DepartementDTO dto) {
        return Departement.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .build();
    }
}
