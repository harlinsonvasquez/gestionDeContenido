package com.example.gestion.de.contenido.RIWI.infrastructure.services;

import com.example.gestion.de.contenido.RIWI.api.dto.request.ClassReq;
import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.domain.entities.ClassEntity;
import com.example.gestion.de.contenido.RIWI.domain.entities.Student;
import com.example.gestion.de.contenido.RIWI.domain.repositories.ClassEntityRepository;
import com.example.gestion.de.contenido.RIWI.domain.repositories.StudentRepository;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IClassService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassEntityService implements IClassService {
    @Autowired
    private final ClassEntityRepository classEntityRepository;
    @Autowired
    private final StudentRepository studentRepository;

    @Override
    public ClassEntityBasic patch(Long id, Status status) {
        ClassEntity classEntity=this.find(id);
        classEntity.setStatus(status); // asigna el nuevo estado
        ClassEntity patch = classEntityRepository.save(classEntity); // guarda el estudiante actualizado
        return this.entityToResp(patch); // convierte la entidad a la respuesta
    }

    @Override
    public ClassEntityBasic create(ClassReq request) {
        ClassEntity classEntity=this.requestToEntity(request);
        return this.entityToResp(this.classEntityRepository.save(classEntity));
    }

    @Override
    public ClassEntityBasic get(Long aLong) {
        return null;
    }

    @Override
    public ClassEntityBasic update(ClassReq request, Long aLong) {
        return null;
    }

    @Override
    public Page<ClassEntityBasic> getAll(int page, int size, SortType sort) {
        PageRequest pageRequest = getPageRequest(page, size, sort);

        return classEntityRepository.findAll(pageRequest).map(this::entityToResp);
    }
    private PageRequest getPageRequest(int page, int size, SortType sort) {
        if (page < 0) page = 0;
        Sort.Direction direction = sort.equals(SortType.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, "id"));
    }
    private ClassEntity find(Long id){
        return this.classEntityRepository.findById(id).orElseThrow(()-> new BadRequestException("no hay registros de clase con ese id"));
    }
    private ClassEntityBasic entityToResp(ClassEntity entity){
        ClassEntityBasic response=new ClassEntityBasic();
        BeanUtils.copyProperties(entity,response);
        return  response;
    }
    private ClassEntity requestToEntity(ClassReq request){
        ClassEntity classEntity=new ClassEntity();
        BeanUtils.copyProperties(request,classEntity);
        return classEntity;
    }
    public List<Student> getStudentsByClassId(Long classId) {
        Optional<ClassEntity> classEntityOptional = classEntityRepository.findById(classId);
        if (classEntityOptional.isPresent()) {
            return classEntityOptional.get().getStudents();
        } else {
            throw new RuntimeException("Class with ID " + classId + " not found");
        }
    }
    public ClassEntityResp getClassWithStudentsById(Long classId) {
        Optional<ClassEntity> classEntityOptional = classEntityRepository.findById(classId);
        if (classEntityOptional.isPresent()) {
            ClassEntity classEntity = classEntityOptional.get();
            ClassEntityResp response = new ClassEntityResp();
            response.setId(classEntity.getId());
            response.setName(classEntity.getName());
            response.setDescription(classEntity.getDescription());
            response.setCreatedAt(classEntity.getCreatedAt());
            response.setStatus(classEntity.getStatus());


            List<StudentBasicResp> studentResponses = classEntity.getStudents().stream()
                    .map(student -> {
                        StudentBasicResp studentResponse = new StudentBasicResp();
                        studentResponse.setId(student.getId());
                        studentResponse.setName(student.getName());
                        studentResponse.setId(student.getClassEntity().getId());
                        return studentResponse;
                    }).collect(Collectors.toList());

            response.setStudents(studentResponses);
            return response;
        } else {
            throw new RuntimeException("Class with ID " + classId + " not found");
        }
    }
}


