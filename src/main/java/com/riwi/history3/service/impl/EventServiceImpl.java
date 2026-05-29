package com.riwi.history3.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riwi.history3.dto.EventSummaryDTO;
import com.riwi.history3.model.Event;
import com.riwi.history3.repository.EventRepository;
import com.riwi.history3.service.EventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Slice<EventSummaryDTO> findAllSummaries(Pageable pageable) {
        return eventRepository.findAllSummaries(pageable);
    }

    @Override
    public Event findByIdWithRelations(Long id) {
        return eventRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con id: " + id));
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    // @Transactional porque modificamos el estado del objeto y necesitamos que
    // Hibernate persista el cambio automáticamente al cerrar la transacción
    @Transactional
    public void softDelete(Long id) {
        Event event = findByIdWithRelations(id);
        // Llama al método deactivate() que definimos en la entidad
        event.deactivate();
        // No necesita save() explícito — Hibernate detecta el cambio (dirty checking)
    }

    @Override
    public Slice<EventSummaryDTO> findByCity(String city, Pageable pageable) {
        return eventRepository.findByCity(city, pageable);
    }

    @Override
    public Slice<EventSummaryDTO> findByCategoryName(String categoryName, Pageable pageable) {
        return eventRepository.findByCategoryName(categoryName, pageable);
    }

    @Override
    public Slice<EventSummaryDTO> findByMinCapacity(Integer capacity, Pageable pageable) {
        return eventRepository.findByMinCapacity(capacity, pageable);
    }

    @Override
    public Slice<EventSummaryDTO> findByDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return eventRepository.findByDateBetween(start, end, pageable);
    }
}