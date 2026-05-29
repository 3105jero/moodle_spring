package com.riwi.history3.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "events")
@SQLRestriction("active = true") // Solo eventos activos
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDateTime eventDate;

    @NotNull(message = "El lugar es obligatorio")
    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;
    // NUEVO — soft delete: true = activo, false = eliminado lógicamente
    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    // NUEVO — relación Many-to-Many con Category
    // La tabla intermedia se llama events_categories con columnas event_id y 
    // category_id

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "events_categories",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>(); 
    // NUEVO — método de borrado lógico: en vez de eliminar, desactiva el evento
    public void deactivate() {
        this.active = false;
    }
}