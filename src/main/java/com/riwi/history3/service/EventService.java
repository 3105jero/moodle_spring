package com.riwi.history3.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.riwi.history3.dto.EventSummaryDTO;
import com.riwi.history3.model.Event;

public interface EventService {

    // Listado paginado con DTO liviano
    Slice<EventSummaryDTO> findAllSummaries(Pageable pageable);

    // Carga completa de un evento con sus relaciones (para editar/ver detalle)
    Event findByIdWithRelations(Long id);

    Event save(Event event);

    // Soft delete — ya no elimina físicamente
    void softDelete(Long id);

    // Filtros de búsqueda
    Slice<EventSummaryDTO> findByCity(String city, Pageable pageable);
    Slice<EventSummaryDTO> findByCategoryName(String categoryName, Pageable pageable);
    Slice<EventSummaryDTO> findByMinCapacity(Integer capacity, Pageable pageable);
    Slice<EventSummaryDTO> findByDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}