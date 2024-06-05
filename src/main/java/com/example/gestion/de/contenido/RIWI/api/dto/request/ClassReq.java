package com.example.gestion.de.contenido.RIWI.api.dto.request;

import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassReq {
    @NotBlank(message = "elnombre de la clase es requerido")
    @Size(min = 1,max = 50)
    private String name;
    @NotBlank(message = "elnombre de la clase es requerido")
    private String description;
    @NotNull(message = "el estado del estudiante es requerido")
    private Status status;
}
