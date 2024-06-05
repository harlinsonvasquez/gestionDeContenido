package com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service;

import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;

public interface IStudentService extends CrudService<StudentReq, StudentResp,Long>{
    public String FIELD_BY_SORT = "name";

    StudentResp patch(Long id, Status status);
}
