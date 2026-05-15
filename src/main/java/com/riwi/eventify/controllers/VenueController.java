package com.riwi.eventify.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.services.VenueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/venues")
@Tag(name = "Lugares", description = "Operaciones relacionadas con la gestión de lugares")
public class VenueController {

    private final VenueService service;

    public VenueController(VenueService service) {
        this.service = service;
    }

    @Operation(summary = "Listar lugares paginados",
               description = "Parámetros: ?page=0&size=10&sort=name,asc")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<Page<Venue>> getAll(
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @Operation(summary = "Obtener un lugar por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lugar encontrado"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venue> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Crear un nuevo lugar")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lugar creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Venue> create(@RequestBody Venue venue) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(venue));
    }

    @Operation(summary = "Actualizar un lugar existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lugar actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venue> update(@PathVariable Long id, @RequestBody Venue venue) {
        return ResponseEntity.ok(service.update(id, venue));
    }

    @Operation(summary = "Eliminar un lugar")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Lugar eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}