package com.riwi.eventify;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.repositories.EventRepository;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        eventRepository.save(new Event(null, "Concierto de Rock", "2026-12-01", "Gran concierto"));
        eventRepository.save(new Event(null, "Festival de Jazz", "2026-11-15", "Festival anual"));
        eventRepository.save(new Event(null, "Obra de Teatro", "2026-10-20", "Obra clásica"));
    }

    // Escenario 1: Persistencia real en BD
    @Test
    void shouldPersistAndRetrieveEvent() {
        Event saved = eventRepository.save(new Event(null, "Opera Magna", "2027-01-10", "Opera italiana"));

        Optional<Event> found = eventRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Opera Magna");
    }

    // Derived Query
    @Test
    void shouldFindEventsByNameContaining() {
        List<Event> results = eventRepository.findByNameContainingIgnoreCase("festival");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Festival de Jazz");
    }

    @Test
    void shouldReturnEmptyListWhenNoMatchFound() {
        List<Event> results = eventRepository.findByNameContainingIgnoreCase("zzz-inexistente");

        assertThat(results).isEmpty();
    }

    // Escenario 3: Paginación con metadatos
    @Test
    void shouldReturnPagedResults() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("name").ascending());

        Page<Event> page = eventRepository.findAll(pageable);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }

    // Escenario 4: Eliminación exitosa
    @Test
    void shouldDeleteEventById() {
        Event event = eventRepository.findAll().get(0);
        Long id = event.getId();

        eventRepository.deleteById(id);

        assertThat(eventRepository.findById(id)).isEmpty();
    }

    // Escenario 2: ID inexistente → vacío
    @Test
    void shouldReturnEmptyForNonExistentId() {
        Optional<Event> result = eventRepository.findById(9999L);

        assertThat(result).isEmpty();
    }
}