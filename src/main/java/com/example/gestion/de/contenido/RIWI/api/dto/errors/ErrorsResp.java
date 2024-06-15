package com.example.gestion.de.contenido.RIWI.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)//para que no haga el equals y el hashcode
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsResp  extends BaseErrorResp {
    private List<Map<String, String>>errors;
}
