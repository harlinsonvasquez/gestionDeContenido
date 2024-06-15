package com.example.gestion.de.contenido.RIWI.infrastructure.services;

import com.example.gestion.de.contenido.RIWI.api.dto.request.LessonReq;
import com.example.gestion.de.contenido.RIWI.api.dto.request.MultimediaReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.LessonResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.MultimediaBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.MultimediaResp;
import com.example.gestion.de.contenido.RIWI.domain.entities.Lesson;
import com.example.gestion.de.contenido.RIWI.domain.entities.Multimedia;
import com.example.gestion.de.contenido.RIWI.domain.repositories.ClassEntityRepository;
import com.example.gestion.de.contenido.RIWI.domain.repositories.LessonRepository;
import com.example.gestion.de.contenido.RIWI.domain.repositories.MultimediaRepository;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.ILessonService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import com.example.gestion.de.contenido.RIWI.utils.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {

    @Autowired
    private final LessonRepository lessonRepository;
    @Autowired
    private final ClassEntityRepository classEntityRepository;


    @Override
    public LessonResp patch(Long id, Status status) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new BadRequestException("Lesson not found with id " + id));
        lesson.setStatus(status);
        if(lesson.getMultimediaList()!=null){
            for (Multimedia multimedia : lesson.getMultimediaList()) {
                multimedia.setStatus(status.IDLE);
            }
        }
        Lesson updatedLesson = lessonRepository.save(lesson);
        return mapToLessonResp(updatedLesson);
    }
    //YA FUNCIONA PARA CAMBIAR DE ESTADO LOS MULTIMEDIAS CON SU LESSON


    @Override
    public LessonResp create(LessonReq request) {
        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setStatus(request.getStatus());

        List<Multimedia> multimediaList = new ArrayList<>();
        for (MultimediaReq multimediaRequest : request.getMultimediaList()) {
            if (multimediaRequest.getType() == null) {
                throw new IllegalArgumentException("Invalid multimedia type");
            }

            Multimedia multimedia = new Multimedia();
            multimedia.setUrl(multimediaRequest.getUrl());
            multimedia.setType(multimediaRequest.getType());
            multimedia.setStatus(multimediaRequest.getStatus());
            //multimedia.setLesson(lesson);
            multimediaList.add(multimedia);
        }

        lesson.setMultimediaList(multimediaList);
        lesson.setClassEntity(classEntityRepository.findById(request.getClassEntity())
                .orElseThrow(() -> new BadRequestException("ClassEntity not found with id " + request.getClassEntity())));
        Lesson savedLesson = lessonRepository.save(lesson);
        return mapToLessonResp(savedLesson);
    }

    @Override
    public LessonResp get(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new BadRequestException("Lesson not found with id " + id));
        return mapToLessonResp(lesson);
    }

    @Transactional
    @Override
    public LessonResp update(LessonReq request, Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new BadRequestException("Lesson not found with id " + id));
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setStatus(request.getStatus());

        // Limpiar la colección existente para manejar eliminaciones de huérfanos
        lesson.getMultimediaList().clear();

        List<Multimedia> multimediaList = new ArrayList<>();
        for (MultimediaReq multimediaRequest : request.getMultimediaList()) {
            if (multimediaRequest.getType() == null) {
                throw new IllegalArgumentException("Invalid multimedia type");
            }

            Multimedia multimedia = new Multimedia();
            multimedia.setUrl(multimediaRequest.getUrl());
            multimedia.setType(multimediaRequest.getType());
            multimedia.setLesson(lesson);  // Asegurar que la lección esté correctamente asignada
            multimedia.setStatus(multimediaRequest.getStatus());
            multimediaList.add(multimedia);
        }

        lesson.getMultimediaList().addAll(multimediaList);  // Agregar todos los elementos a la colección existente
        Lesson updatedLesson = lessonRepository.save(lesson);
        return mapToLessonResp(updatedLesson);
    }


    @Override
    public Page<LessonResp> getAll(int page, int size, SortType sort) {
        Pageable pageable = getPageable(page, size, sort);
        Page<Lesson> lessons = lessonRepository.findAll(pageable);
        return lessons.map(this::mapToLessonResp);
    }

    private Pageable getPageable(int page, int size, SortType sort) {
        Sort.Direction direction = sort.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, "id"));
    }



    private LessonResp mapToLessonResp(Lesson lesson) {
        LessonResp lessonResponse = new LessonResp();
        lessonResponse.setId(lesson.getId());
        lessonResponse.setTitle(lesson.getTitle());
        lessonResponse.setContent(lesson.getContent());
        lessonResponse.setStatus(lesson.getStatus());
        lessonResponse.setCreatedAt(lesson.getCreatedAt());

        // Mapeo de Multimedia
        if (lesson.getMultimediaList() != null) {
            List<MultimediaResp> multimediaResponses = new ArrayList<>();
            for (Multimedia multimedia : lesson.getMultimediaList()) {
                MultimediaResp multimediaResponse = new MultimediaResp();
                multimediaResponse.setId(multimedia.getId());
                multimediaResponse.setUrl(multimedia.getUrl());
                multimediaResponse.setType(multimedia.getType());
                multimediaResponse.setStatus(multimedia.getStatus());
                multimediaResponse.setCreatedAt(multimedia.getCreatedAt());
                multimediaResponses.add(multimediaResponse);
            }
            lessonResponse.setMultimediaList(multimediaResponses);
        }

        // Mapeo de Clase
        if (lesson.getClassEntity() != null) {
            ClassEntityBasic classEntityResp = new ClassEntityBasic();
            classEntityResp.setId(lesson.getClassEntity().getId());
            classEntityResp.setName(lesson.getClassEntity().getName());
            classEntityResp.setDescription(lesson.getClassEntity().getDescription());
            classEntityResp.setCreatedAt(lesson.getClassEntity().getCreatedAt());
            classEntityResp.setStatus(lesson.getClassEntity().getStatus());
            lessonResponse.setClassEntity(classEntityResp);
        }

        return lessonResponse;
    }
}
