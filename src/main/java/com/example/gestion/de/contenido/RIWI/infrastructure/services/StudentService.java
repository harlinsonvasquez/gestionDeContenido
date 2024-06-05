
package com.example.gestion.de.contenido.RIWI.infrastructure.services;

import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.domain.entities.ClassEntity;
import com.example.gestion.de.contenido.RIWI.domain.entities.Student;
import com.example.gestion.de.contenido.RIWI.domain.repositories.ClassEntityRepository;
import com.example.gestion.de.contenido.RIWI.domain.repositories.StudentRepository;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IStudentService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import com.example.gestion.de.contenido.RIWI.utils.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final ClassEntityRepository classEntityRepository;

    @Override
    public StudentResp create(StudentReq request) {
        Student student = this.requestToEntity(request);
        return this.entityToResp(this.studentRepository.save(student));
    }

    @Override
    public StudentResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public StudentResp update(StudentReq request, Long id) {
        return null;
    }

    @Override
    public StudentResp patch(Long id, Status status) {
        Student student = this.find(id);
        student.setStatus(status); // asigna el nuevo estado
        Student patch = studentRepository.save(student); // guarda el estudiante actualizado
        return this.entityToResp(patch); // convierte la entidad a la respuesta
    }

    @Override
    @Transactional
    public Page<StudentResp> getAll(int page, int size, SortType sort) {
        PageRequest pageRequest = getPageRequest(page, size, sort);
        return studentRepository.findAll(pageRequest).map(this::entityToResp);
    }

    private PageRequest getPageRequest(int page, int size, SortType sort) {
        if (page < 0) page = 0;
        Sort.Direction direction = sort.equals(SortType.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, "id"));
    }

    private Student find(Long id) {
        return this.studentRepository.findById(id).orElseThrow(() -> new BadRequestException("no hay registros de usuarios con ese id"));
    }

    private StudentResp entityToResp(Student entity) {
        ClassEntityBasic classEntityBasic = null;
        ClassEntity classEntity = entity.getClassEntity();

        if (classEntity != null) {
            classEntityBasic = ClassEntityBasic.builder()
                    .id(classEntity.getId())
                    .name(classEntity.getName())
                    .description(classEntity.getDescription())
                    .createdAt(classEntity.getCreatedAt())
                    .build();
        }

        StudentResp response = new StudentResp();
        BeanUtils.copyProperties(entity, response);
        response.setClaseId(classEntityBasic);
        return response;
    }

    private Student requestToEntity(StudentReq request) {
        Student student = new Student();
        BeanUtils.copyProperties(request, student);
        return student;
    }
}

