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
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;
import com.riwi.eventify.services.VenueService;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {

    @Mock
    private VenueRepository repository;

    @InjectMocks
    private VenueService service;

    @Test
    void shouldCreateVenueSuccessfully() {
        Venue venue = new Venue(null, "Teatro Nacional", "Calle 26 #5-60, Bogotá", 500);
        Venue saved  = new Venue(1L,  "Teatro Nacional", "Calle 26 #5-60, Bogotá", 500);
        when(repository.save(venue)).thenReturn(saved);

        Venue result = service.create(venue);

        assertNotNull(result);
        assertEquals("Teatro Nacional", result.getName());
        verify(repository, times(1)).save(venue);
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Venue venue = new Venue(null, "", "Calle 26 #5-60, Bogotá", 500);

        assertThrows(IllegalArgumentException.class, () -> service.create(venue));
        verify(repository, never()).save(venue);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Venue venue = new Venue(null, null, "Calle 26 #5-60, Bogotá", 500);

        assertThrows(IllegalArgumentException.class, () -> service.create(venue));
        verify(repository, never()).save(venue);
    }

    @Test
    void shouldReturnPagedVenuesWhenGetAllIsCalled() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Venue> page = new PageImpl<>(List.of(
            new Venue(1L, "Teatro Nacional", "Bogotá", 500)
        ));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Venue> result = service.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldReturnVenueWhenGetByIdFindsIt() {
        Venue venue = new Venue(1L, "Teatro Nacional", "Bogotá", 500);
        when(repository.findById(1L)).thenReturn(Optional.of(venue));

        Venue result = service.getById(1L);

        assertEquals("Teatro Nacional", result.getName());
    }

    // Escenario 2: 404 Not Found
    @Test
    void shouldThrow404WhenVenueNotFound() {
        when(repository.findById(9999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(9999L));
    }

    @Test
    void shouldUpdateVenueSuccessfully() {
        Venue existing = new Venue(1L, "Viejo Nombre", "Dirección vieja", 100);
        Venue updated  = new Venue(null, "Nuevo Nombre", "Nueva dirección", 200);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Venue result = service.update(1L, updated);

        assertEquals("Nuevo Nombre", result.getName());
        assertEquals(200, result.getCapacity());
    }

    @Test
    void shouldDeleteVenueWhenItExists() {
        Venue venue = new Venue(1L, "Teatro Nacional", "Bogotá", 500);
        when(repository.findById(1L)).thenReturn(Optional.of(venue));

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}