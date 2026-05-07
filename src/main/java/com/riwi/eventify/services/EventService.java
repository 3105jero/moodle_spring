package com.riwi.eventify.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.repositories.EventRepository;

@Service
public class EventService {

    private final EventRepository repository;

    // Inyección por constructor
    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<Event> getAll() {
        return repository.findAll();
    }

    public Event create(Event event) {
        // Validación: el nombre no puede estar vacío
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new RuntimeException("El nombre del evento es obligatorio");
        }
        return repository.save(event);
    }
}