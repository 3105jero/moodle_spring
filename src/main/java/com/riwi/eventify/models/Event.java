package com.riwi.eventify.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
@Schema(description = "Modelo que representa un evento en el sistema")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del evento", example = "1")
    private Long id;

    @Column(nullable = false, length = 150)
    @Schema(description = "Nombre del evento", example = "Concierto de Rock")
    private String name;

    @Column(nullable = false, length = 20)
    @Schema(description = "Fecha del evento (YYYY-MM-DD)", example = "2026-12-01")
    private String date;

    @Column(length = 500)
    @Schema(description = "Descripción del evento", example = "Gran concierto de rock en vivo")
    private String description;
}