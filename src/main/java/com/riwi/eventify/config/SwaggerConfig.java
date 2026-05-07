package com.riwi.eventify.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Eventify",
        version = "1.0",
        description = "Plataforma para la gestión de eventos y lugares.",
        contact = @Contact(name = "Soporte Riwi", email = "soporte@riwi.io")
    )
)
public class SwaggerConfig {
}