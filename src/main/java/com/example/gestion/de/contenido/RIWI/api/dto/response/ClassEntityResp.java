package com.example.gestion.de.contenido.RIWI.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ClassEntityResp extends ClassEntityBasic {
    private List<StudentBasicResp> students;
}
