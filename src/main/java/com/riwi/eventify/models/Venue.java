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
@Table(name = "venues")
@Schema(description = "Modelo que representa un lugar en el sistema")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del lugar", example = "1")
    private Long id;

    @Column(nullable = false, length = 150)
    @Schema(description = "Nombre del lugar", example = "Teatro Nacional")
    private String name;

    @Column(nullable = false, length = 300)
    @Schema(description = "Dirección del lugar", example = "Calle 26 #5-60, Bogotá")
    private String address;

    @Column(nullable = false)
    @Schema(description = "Capacidad máxima del lugar", example = "500")
    private Integer capacity;
}