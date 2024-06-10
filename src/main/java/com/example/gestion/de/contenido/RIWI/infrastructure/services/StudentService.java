
package com.example.gestion.de.contenido.RIWI.infrastructure.services;

import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
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

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final ClassEntityRepository classEntityRepository;

    //metodo para crear el PageRequest con los parametros de paginacion y ordenamiento
    private PageRequest getPageRequest(int page, int size, SortType sort) {
        if (page < 0) page = 0;
        Sort.Direction direction = sort.equals(SortType.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, "id"));
    }
    @Override
    public Page<StudentBasicResp> getAllBasic(int page, int size, SortType sort, String name) {
        //llama al metodo getPageRequest para crear el PageRequest con los parametros de paginacion y ordenamiento
        PageRequest pageRequest = getPageRequest(page, size, sort);

        Page<Student> studentsPage;// variable que almacenara la informacion de los estudiantes traidas de la base de datos

        //si el nombre no es nulo o vacio, se filtra por el nombre y status, de lo contrario solo se filtra por el status
        if (name != null && !name.isEmpty()) {
            studentsPage = studentRepository.findByStatusAndName(Status.ASSET, name, pageRequest);
        } else {
            studentsPage = studentRepository.findByStatus(Status.ASSET, pageRequest);
        }
        //se utiliza el metodo map para convertir cada student a StudentBasicResp y se retornan los resultados
        return studentsPage.map(this::entityToBasicResp);
    }

    @Override
    @Transactional
    public Page<StudentResp> getAll(int page, int size, SortType sort) {
        PageRequest pageRequest = getPageRequest(page, size, sort);
        Page<Student> studentsPage = studentRepository.findByStatus(Status.ASSET, pageRequest);
        return studentsPage.map(this::entityToResp);
    }

    @Override
    public StudentResp create(StudentReq request) {
        ClassEntity classEntity = this.classEntityRepository.findById(request.getClassEntity())
                .orElseThrow(() -> new BadRequestException("no hay registros de clases con ese id"));
        Student student = this.requestToEntity(request);
        student.setClassEntity(classEntity);
        return this.entityToResp(this.studentRepository.save(student));
    }

    @Override
    public StudentResp patch(Long id, Status status) {
        Student student = this.find(id);
        student.setStatus(status); // asigna el nuevo estado
        Student patch = studentRepository.save(student); // guarda el estudiante actualizado
        return this.entityToResp(patch); // convierte la entidad a la respuesta
    }

    @Override
    public StudentResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public StudentResp update(StudentReq request, Long id) {
        Student student = this.find(id);
        ClassEntity classEntity = this.classEntityRepository.findById(request.getClassEntity())
                .orElseThrow(() -> new BadRequestException("no hay registros de clases con ese id"));
        student=this.requestToEntity(request);
        student.setClassEntity(classEntity);
        student.setCreatedAt(LocalDateTime.now());//tomar la fecha actual y hora en que se actualiza el estudiante
        student.setId(id);
        return this.entityToResp(this.studentRepository.save(student));

    }

    //metodo para buscar un estudiante por id
    private Student find(Long id) {
        return this.studentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("no hay registros de usuarios con ese id"));
    }

    private StudentResp entityToResp(Student entity) {
        ClassEntityBasic classEntity=new ClassEntityBasic();
        BeanUtils.copyProperties(entity.getClassEntity(), classEntity);

        return StudentResp.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .classEntity(classEntity)
                .status(entity.getStatus())
                .build();
    }

    private StudentBasicResp entityToBasicResp(Student entity) {
        StudentBasicResp response = new StudentBasicResp();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    private Student requestToEntity(StudentReq request) {
        Student student = new Student();
        BeanUtils.copyProperties(request, student);
        return student;
    }
}

