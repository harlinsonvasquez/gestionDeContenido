package com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service;

import com.example.gestion.de.contenido.RIWI.api.dto.request.LessonReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.LessonResp;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import org.springframework.data.domain.Page;

public interface ILessonService extends CrudService<LessonReq, LessonResp,Long>{
   LessonResp patch(Long id, Status status);


}
