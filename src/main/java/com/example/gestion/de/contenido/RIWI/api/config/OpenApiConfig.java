package com.example.gestion.de.contenido.RIWI.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration//para configurar beans dentro de spring
@OpenAPIDefinition(
        info = @Info(title = "Api para gestion de contenido",
                version = "1.0",
                description = "Api para gestion de contenido de estudiantes,cursos,lesiones y contenido multimedia")
)
public class OpenApiConfig {
}
