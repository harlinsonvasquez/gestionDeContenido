package com.example.gestion.de.contenido.RIWI.domain.repositories;

import com.example.gestion.de.contenido.RIWI.domain.entities.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Page<Lesson> findAll(Pageable pageable);
}
