package com.riwi.history3.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.history3.dto.EventSummaryDTO;
import com.riwi.history3.model.Event;
import com.riwi.history3.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
// Agrupa todos los endpoints bajo "Eventos" en Swagger UI
@Tag(name = "Eventos", description = "Gestión del catálogo de eventos de Eventify")
public class EventRestController {

    private final EventService eventService;

    @GetMapping
    @Operation(
        summary = "Listar eventos paginados",
        description = "Retorna un Slice de EventSummaryDTO ordenado por fecha descendente. " +
                      "Los eventos con borrado lógico (active=false) son excluidos automáticamente."
    )
    public ResponseEntity<Slice<EventSummaryDTO>> findAll(
            @Parameter(description = "Número de página (empieza en 0)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de registros por página") 
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.findAllSummaries(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener evento por ID",
        description = "Carga el evento completo con venue y categorías en una sola query usando @EntityGraph."
    )
    public ResponseEntity<Event> findById(
            @Parameter(description = "ID del evento") @PathVariable Long id) {
        return ResponseEntity.ok(eventService.findByIdWithRelations(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo evento")
    public ResponseEntity<Event> save(@Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventService.save(event));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar evento (borrado lógico)",
        description = "No elimina el registro físicamente. Cambia active=false. " +
                      "El evento deja de aparecer en todas las consultas automáticamente."
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del evento a desactivar") @PathVariable Long id) {
        eventService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Filtros de búsqueda ──────────────────────────────────────

    @GetMapping("/search/city")
    @Operation(
        summary = "Buscar por ciudad",
        description = "Búsqueda parcial e insensible a mayúsculas. Ej: 'bog' encuentra 'Bogotá'."
    )
    public ResponseEntity<Slice<EventSummaryDTO>> findByCity(
            @Parameter(description = "Nombre parcial o completo de la ciudad")
            @RequestParam String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByCity(city, PageRequest.of(page, size)));
    }

    @GetMapping("/search/category")
    @Operation(
        summary = "Buscar por categoría",
        description = "Búsqueda parcial e insensible a mayúsculas. Ej: 'rock' encuentra 'Concierto de ROCK'."
    )
    public ResponseEntity<Slice<EventSummaryDTO>> findByCategory(
            @Parameter(description = "Nombre parcial o completo de la categoría")
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByCategoryName(categoryName, PageRequest.of(page, size)));
    }

    @GetMapping("/search/capacity")
    @Operation(summary = "Buscar por capacidad mínima del venue")
    public ResponseEntity<Slice<EventSummaryDTO>> findByCapacity(
            @Parameter(description = "Capacidad mínima requerida")
            @RequestParam Integer capacity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByMinCapacity(capacity, PageRequest.of(page, size)));
    }

    @GetMapping("/search/dates")
    @Operation(summary = "Buscar por rango de fechas")
    public ResponseEntity<Slice<EventSummaryDTO>> findByDates(
            @Parameter(description = "Fecha inicio (formato: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @Parameter(description = "Fecha fin (formato: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByDateBetween(start, end, PageRequest.of(page, size)));
    }
}