package com.riwi.eventify;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.repositories.EventRepository;
import com.riwi.eventify.services.EventService;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService service;

    // Escenario 1: Registro exitoso (Camino Feliz)
    @Test
    void shouldCreateEventSuccessfully() {
        Event event = new Event(1L, "Concierto de Rock", "2026-12-01", "Gran concierto en vivo");
        when(repository.save(event)).thenReturn(event);

        Event result = service.create(event);

        assertNotNull(result);
        assertEquals("Concierto de Rock", result.getName());
        verify(repository, times(1)).save(event);
    }

    // Escenario 2: Nombre vacío debe lanzar excepción (Camino de Error)
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Event event = new Event(1L, "", "2026-12-01", "Descripción");

        assertThrows(RuntimeException.class, () -> service.create(event));
        verify(repository, never()).save(event);
    }

    // Escenario 2: Nombre null debe lanzar excepción (Camino de Error)
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Event event = new Event(1L, null, "2026-12-01", "Descripción");

        assertThrows(RuntimeException.class, () -> service.create(event));
        verify(repository, never()).save(event);
    }

    // Escenario 3: Catálogo vacío retorna lista vacía con 200 OK (Caso de Borde)
    @Test
    void shouldReturnEmptyListWhenNoEventsExist() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Event> result = service.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }
}
