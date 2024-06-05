package com.example.gestion.de.contenido.RIWI.infrastructure.services;

import com.example.gestion.de.contenido.RIWI.api.dto.request.LessonReq;
import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.LessonBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.LessonResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.domain.entities.ClassEntity;
import com.example.gestion.de.contenido.RIWI.domain.entities.Lesson;
import com.example.gestion.de.contenido.RIWI.domain.entities.Student;
import com.example.gestion.de.contenido.RIWI.domain.repositories.LessonRepository;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.ILessonService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import com.example.gestion.de.contenido.RIWI.utils.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {
    @Autowired
    private final LessonRepository lessonRepository;
    @Override
    public LessonResp patch(Long id, Status status) {
        return null;
    }

    @Override
    public LessonResp create(LessonReq request) {
        return null;
    }

    @Override
    public LessonResp get(Long aLong) {
        return null;
    }

    @Override
    public LessonResp update(LessonReq request, Long aLong) {
        return null;
    }

    @Override
    public Page<LessonResp> getAll(int page, int size, SortType sort) {
        return null;
    }
    private Lesson find(Long id){
        return  this.lessonRepository.findById(id).orElseThrow(()->new BadRequestException("no hay registros de lesson con ese id"));
    }

    private Lesson requestToEntity( LessonReq request) {
        Lesson lesson = new Lesson();
        BeanUtils.copyProperties(request, lesson);
        return lesson;
    }

}
