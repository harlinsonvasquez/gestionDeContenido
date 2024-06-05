package com.example.gestion.de.contenido.RIWI.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonResp {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private ClassEntityBasic classEntity;
    private List<MultimediaResp> multimedia;
}
