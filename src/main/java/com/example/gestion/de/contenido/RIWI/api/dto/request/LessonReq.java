package com.example.gestion.de.contenido.RIWI.api.dto.request;

import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonReq {
    @NotBlank(message = "el titulo de la lección es requerido")
    private String title;
    private String content;
    private Long classEntity;
    private Status status;
    private List<MultimediaReq> multimediaList;


}
