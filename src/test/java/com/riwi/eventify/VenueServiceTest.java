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

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;
import com.riwi.eventify.services.VenueService;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {

    @Mock
    private VenueRepository repository;

    @InjectMocks
    private VenueService service;

    // Escenario 1: Registro exitoso (Camino Feliz)
    @Test
    void shouldCreateVenueSuccessfully() {
        Venue venue = new Venue(1L, "Teatro Nacional", "Calle 26 #5-60, Bogotá", 500);
        when(repository.save(venue)).thenReturn(venue);

        Venue result = service.create(venue);

        assertNotNull(result);
        assertEquals("Teatro Nacional", result.getName());
        verify(repository, times(1)).save(venue);
    }

    // Escenario 2: Nombre vacío debe lanzar excepción (Camino de Error)
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Venue venue = new Venue(1L, "", "Calle 26 #5-60, Bogotá", 500);

        assertThrows(RuntimeException.class, () -> service.create(venue));
        verify(repository, never()).save(venue);
    }

    // Escenario 2: Nombre null debe lanzar excepción (Camino de Error)
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Venue venue = new Venue(1L, null, "Calle 26 #5-60, Bogotá", 500);

        assertThrows(RuntimeException.class, () -> service.create(venue));
        verify(repository, never()).save(venue);
    }

    // Escenario 3: Catálogo vacío retorna lista vacía con 200 OK (Caso de Borde)
    @Test
    void shouldReturnEmptyListWhenNoVenuesExist() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Venue> result = service.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }
}
