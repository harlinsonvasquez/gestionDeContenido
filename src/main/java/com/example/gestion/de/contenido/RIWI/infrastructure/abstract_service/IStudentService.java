package com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service;

import com.example.gestion.de.contenido.RIWI.api.dto.request.StudentReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentBasicResp;
import com.example.gestion.de.contenido.RIWI.api.dto.response.StudentResp;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import org.springframework.data.domain.Page;

public interface IStudentService extends CrudService<StudentReq, StudentResp,Long>{

    StudentResp patch(Long id, Status status);
    //este metodo se usa en el controller y se implementa en el service
    Page<StudentBasicResp> getAllBasic(int page, int size, SortType sort, String name);
    //este metodo lo declaro aqui y lo uso en service porque IStudentService DEVUELVE POR DEFECTO ES StudenResp y
    //yo necesito responder con StudentBasicResp en algunas solicitudes

}
