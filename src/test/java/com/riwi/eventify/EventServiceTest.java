package com.riwi.eventify;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.riwi.eventify.exceptions.ResourceNotFoundException;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.repositories.EventRepository;
import com.riwi.eventify.services.EventService;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService service;

    @Test
    void shouldCreateEventSuccessfully() {
        Event event = new Event(null, "Concierto de Rock", "2026-12-01", "Gran concierto en vivo");
        Event saved  = new Event(1L,  "Concierto de Rock", "2026-12-01", "Gran concierto en vivo");
        when(repository.save(event)).thenReturn(saved);

        Event result = service.create(event);

        assertNotNull(result);
        assertEquals("Concierto de Rock", result.getName());
        verify(repository, times(1)).save(event);
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Event event = new Event(null, "", "2026-12-01", "Descripción");

        assertThrows(IllegalArgumentException.class, () -> service.create(event));
        verify(repository, never()).save(event);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Event event = new Event(null, null, "2026-12-01", "Descripción");

        assertThrows(IllegalArgumentException.class, () -> service.create(event));
        verify(repository, never()).save(event);
    }

    @Test
    void shouldReturnPagedEventsWhenGetAllIsCalled() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Event> page = new PageImpl<>(List.of(
            new Event(1L, "Concierto de Rock", "2026-12-01", "Gran concierto")
        ));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Event> result = service.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldReturnEventWhenGetByIdFindsIt() {
        Event event = new Event(1L, "Concierto de Rock", "2026-12-01", "Gran concierto");
        when(repository.findById(1L)).thenReturn(Optional.of(event));

        Event result = service.getById(1L);

        assertEquals("Concierto de Rock", result.getName());
    }

    // Escenario 2: 404 Not Found
    @Test
    void shouldThrow404WhenEventNotFound() {
        when(repository.findById(9999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(9999L));
    }

    @Test
    void shouldUpdateEventSuccessfully() {
        Event existing = new Event(1L, "Viejo Nombre", "2026-01-01", "Descripción vieja");
        Event updated  = new Event(null, "Nuevo Nombre", "2026-06-15", "Nueva descripción");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Event result = service.update(1L, updated);

        assertEquals("Nuevo Nombre", result.getName());
    }

    @Test
    void shouldDeleteEventWhenItExists() {
        Event event = new Event(1L, "Concierto de Rock", "2026-12-01", "Gran concierto");
        when(repository.findById(1L)).thenReturn(Optional.of(event));

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}