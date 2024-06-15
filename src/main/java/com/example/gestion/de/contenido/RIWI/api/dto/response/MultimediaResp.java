package com.example.gestion.de.contenido.RIWI.api.dto.response;

import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import com.example.gestion.de.contenido.RIWI.utils.enums.TypeMultimedia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MultimediaResp {
    private Long id;
    private TypeMultimedia type;
    private String url;
    private LocalDateTime createdAt;
    private Status status;
}
