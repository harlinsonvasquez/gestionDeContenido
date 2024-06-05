package com.example.gestion.de.contenido.RIWI.api.dto.request;

import com.example.gestion.de.contenido.RIWI.api.dto.response.ClassEntityBasic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonReq {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private ClassEntityBasic classEntity;
}
