package com.example.gestion.de.contenido.RIWI.api.dto.response;

import com.example.gestion.de.contenido.RIWI.domain.entities.ClassEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResp extends StudentBasicResp {
    private ClassEntity clase;
}
