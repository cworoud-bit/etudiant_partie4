package com.example.grading.service;

import com.example.grading.config.EtudiantClient;
import com.example.grading.config.ResourceNotFoundException;
import com.example.grading.dto.NoteDTO;
import com.example.grading.entity.Note;
import com.example.grading.mapper.NoteMapper;
import com.example.grading.repository.NoteRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository repository;
    private final NoteMapper mapper;
    private final EtudiantClient etudiantClient;

    public List<NoteDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public List<NoteDTO> findByStudentId(Long studentId) {
        return repository.findByStudentId(studentId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public NoteDTO findById(Long id) {
        Note note = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note introuvable avec l'ID : " + id));
        return mapper.toDTO(note);
    }

    public NoteDTO save(NoteDTO dto) {
        // Vérifier existence de l'étudiant via Feign → Eureka
        try {
            etudiantClient.getEtudiantById(dto.getStudentId());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Étudiant introuvable avec l'ID : " + dto.getStudentId());
        }
        Note saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public NoteDTO update(Long id, NoteDTO dto) {
        Note existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note introuvable avec l'ID : " + id));
        existing.setMatiere(dto.getMatiere());
        existing.setValeur(dto.getValeur());
        return mapper.toDTO(repository.save(existing));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Note introuvable avec l'ID : " + id);
        }
        repository.deleteById(id);
    }
}
