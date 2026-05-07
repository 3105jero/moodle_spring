package com.riwi.eventify.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;

@Service
public class VenueService {

    private final VenueRepository repository;

    // Inyección por constructor
    public VenueService(VenueRepository repository) {
        this.repository = repository;
    }

    public List<Venue> getAll() {
        return repository.findAll();
    }

    public Venue create(Venue venue) {
        // Validación: el nombre no puede estar vacío
        if (venue.getName() == null || venue.getName().isEmpty()) {
            throw new RuntimeException("El nombre del lugar es obligatorio");
        }
        return repository.save(venue);
    }
}