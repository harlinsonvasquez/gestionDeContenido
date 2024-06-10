package com.example.gestion.de.contenido.RIWI.domain.repositories;

import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.domain.entities.ClassEntity;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassEntityRepository extends JpaRepository<ClassEntity,Long> {
    //metodo para buscar una clase por estado
    Page<ClassEntity> findByStatus(Status status, Pageable pageable);

    Page<ClassEntity> findByNameContainingIgnoreCaseAndStatus(String name, Status status, Pageable pageable);

    Page<ClassEntity> findByDescriptionContainingIgnoreCaseAndStatus(String description, Status status, Pageable pageable);

    Page<ClassEntity> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndStatus(
            String name, String description, Status status, Pageable pageable);


}
