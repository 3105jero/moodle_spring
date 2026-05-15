package com.riwi.eventify.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.eventify.exceptions.ResourceNotFoundException;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.repositories.EventRepository;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    // READ (paginado)
    public Page<Event> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // READ por ID
    public Event getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
    }

    // CREATE
    public Event create(Event event) {
        if (event.getName() == null || event.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del evento es obligatorio");
        }
        event.setId(null); // la BD genera el ID
        return repository.save(event);
    }

    // UPDATE
    public Event update(Long id, Event updated) {
        Event existing = getById(id); // lanza 404 si no existe

        if (updated.getName() == null || updated.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del evento es obligatorio");
        }

        existing.setName(updated.getName());
        existing.setDate(updated.getDate());
        existing.setDescription(updated.getDescription());

        return repository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        getById(id); // lanza 404 si no existe
        repository.deleteById(id);
    }
}