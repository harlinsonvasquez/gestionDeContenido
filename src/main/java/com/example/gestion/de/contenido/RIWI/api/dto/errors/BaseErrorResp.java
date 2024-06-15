package com.example.gestion.de.contenido.RIWI.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
/*Serializable clase especial para responder por http */
public class BaseErrorResp  implements Serializable {
    private String status;
    private Integer code;
}
