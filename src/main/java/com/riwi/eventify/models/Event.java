package com.riwi.eventify.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa un evento en el sistema")
public class Event {

    @Schema(description = "ID único del evento", example = "1")
    private Long id;

    @Schema(description = "Nombre del evento", example = "Concierto de Rock")
    private String name;

    @Schema(description = "Fecha del evento", example = "2026-12-01")
    private String date;

    @Schema(description = "Descripción del evento", example = "Gran concierto de rock en vivo")
    private String description;
}