package com.riwi.eventify.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa un lugar en el sistema")
public class Venue {

    @Schema(description = "ID único del lugar", example = "1")
    private Long id;

    @Schema(description = "Nombre del lugar", example = "Teatro Nacional")
    private String name;

    @Schema(description = "Dirección del lugar", example = "Calle 26 #5-60, Bogotá")
    private String address;

    @Schema(description = "Capacidad máxima del lugar", example = "500")
    private Integer capacity;
}