package com.riwi.history3.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.riwi.history3.model.Venue;
import com.riwi.history3.repository.VenueRepository;
import com.riwi.history3.service.VenueService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    @Override
    public Venue findById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado con id: " + id));
    }

    @Override
    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }
}