package com.example.gestion.de.contenido.RIWI.api.dto.request;

import com.example.gestion.de.contenido.RIWI.utils.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentReq {
    @NotBlank(message = "elnombre del estudiante es requerido")
    @Size(min = 1,max = 50)
    private String name;
    @Email()
    private String email;

    @NotNull(message = "el estado del estudiante es requerido")
    private Status status;
    private Long classId;
}
