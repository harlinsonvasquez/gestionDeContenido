package com.example.gestion.de.contenido.RIWI.api.dto.response;

import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
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
public class    LessonResp {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Status status;
    private ClassEntityBasic classEntity;

    private List<MultimediaResp> multimediaList;


}
