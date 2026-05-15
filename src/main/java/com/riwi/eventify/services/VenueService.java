package com.riwi.eventify.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.eventify.exceptions.ResourceNotFoundException;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;

@Service
public class VenueService {

    private final VenueRepository repository;

    public VenueService(VenueRepository repository) {
        this.repository = repository;
    }

    // READ (paginado)
    public Page<Venue> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // READ por ID
    public Venue getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar", id));
    }

    // CREATE
    public Venue create(Venue venue) {
        if (venue.getName() == null || venue.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del lugar es obligatorio");
        }
        venue.setId(null);
        return repository.save(venue);
    }

    // UPDATE
    public Venue update(Long id, Venue updated) {
        Venue existing = getById(id);

        if (updated.getName() == null || updated.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del lugar es obligatorio");
        }

        existing.setName(updated.getName());
        existing.setAddress(updated.getAddress());
        existing.setCapacity(updated.getCapacity());

        return repository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }
}