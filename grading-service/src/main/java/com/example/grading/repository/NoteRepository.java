package com.example.grading.repository;

import com.example.grading.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByStudentId(Long studentId);
}
