package com.example.grading.repository;

import com.example.grading.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // T'najjem t'zid hethi bech t'lawweb 3la les notes mta' etudiant wa7ed
    List<Note> findByStudentId(Long studentId);
}