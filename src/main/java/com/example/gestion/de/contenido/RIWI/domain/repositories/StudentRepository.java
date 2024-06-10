package com.example.gestion.de.contenido.RIWI.domain.repositories;

import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.domain.entities.Student;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Page<Student> findByStatusAndName(Status status, String name, Pageable pageable);
    //metodo para buscar un estudiante por nombre y estado
    Page<Student> findByStatus(Status status, Pageable pageable);
    //metodo para buscar un estudiante por estado y ser usado en el cambio de estado en el servicio


}

