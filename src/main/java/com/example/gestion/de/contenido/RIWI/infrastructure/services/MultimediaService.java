package com.example.gestion.de.contenido.RIWI.infrastructure.services;

import com.example.gestion.de.contenido.RIWI.api.dto.request.MultimediaReq;
import com.example.gestion.de.contenido.RIWI.api.dto.response.MultimediaBasicResp;
import com.example.gestion.de.contenido.RIWI.domain.entities.Multimedia;
import com.example.gestion.de.contenido.RIWI.domain.repositories.LessonRepository;
import com.example.gestion.de.contenido.RIWI.domain.repositories.MultimediaRepository;
import com.example.gestion.de.contenido.RIWI.infrastructure.abstract_service.IMultimediaService;
import com.example.gestion.de.contenido.RIWI.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MultimediaService implements IMultimediaService {

    @Autowired
    private final MultimediaRepository multimediaRepository;
    @Autowired
    private final LessonRepository lessonRepository;
    @Override
    public MultimediaBasicResp create(MultimediaReq request) {
        Multimedia multimedia = requestToEntity(request);
        multimedia = multimediaRepository.save(multimedia);
        return entityToResp(multimedia);
    }
    private Multimedia requestToEntity(MultimediaReq request) {
        Multimedia multimedia = new Multimedia();
        BeanUtils.copyProperties(request, multimedia);
        return multimedia;
    }
    private MultimediaBasicResp entityToResp(Multimedia entity) {
        return MultimediaBasicResp.builder()
                .id(entity.getId())
                .type(entity.getType())
                .url(entity.getUrl())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public MultimediaBasicResp get(Long aLong) {
        return null;
    }

    @Override
    public MultimediaBasicResp update(MultimediaReq request, Long aLong) {
        return null;
    }

    @Override
    public Page<MultimediaBasicResp> getAll(int page, int size, SortType sort) {
        return null;
    }

//falta cambiar el response y convertir la lesson para responder el response de multimedia con la lesson asociada
    //borre el service de lesson porque no funcionaba
}
