package com.example.gestion.de.contenido.RIWI.api.dto.request;

import com.example.gestion.de.contenido.RIWI.api.dto.response.LessonResp;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import com.example.gestion.de.contenido.RIWI.utils.enums.TypeMultimedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultimediaReq {
    private Long id;
    private TypeMultimedia type;
    private String url;
    private Status status;
    private Long lessonId;


}
