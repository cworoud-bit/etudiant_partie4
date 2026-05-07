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
@RequiredArgsConstructor // Ta3mel injection wa7edha lel variables 'final'
public class NoteService {

    // Khalli hethom barka w zidhom 'final'
    private final NoteRepository repository;
    private final NoteMapper mapper;
    private final EtudiantClient etudiantClient;

    public NoteDTO save(NoteDTO dto) {
        try {
            // Feign bech ylawweb 3la "etudiant-service" fi Eureka
            etudiantClient.getEtudiantById(dto.getStudentId());
        } catch (Exception e) {
            // Ken Eureka mazel ma feyi7ch bel service wala l'issem ghalet, bech ya3tik hna error
            throw new IllegalArgumentException("Étudiant introuvable wala service taye7!");
        }
        Note saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    // Na77i el khedma el zayda l-okhra elli maktouba l-louta
}