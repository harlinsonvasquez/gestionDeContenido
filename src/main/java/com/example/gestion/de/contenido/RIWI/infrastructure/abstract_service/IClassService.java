package com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service;

import com.example.gestion.de.contenido.RIWI.api.dto.request.ClassReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import org.springframework.data.domain.Page;

public interface IClassService extends CrudService<ClassReq, ClassEntityBasic,Long>{
    ClassEntityBasic patch(Long id, Status status);

    ClassEntityResp getClassWithStudentsById(Long id);
    Page<ClassEntityBasic> getAllBasic(int page, int size, SortType sort, String name, String description);

}
