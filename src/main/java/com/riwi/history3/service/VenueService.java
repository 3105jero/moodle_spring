package com.riwi.history3.service;

import java.util.List;

import com.riwi.history3.model.Venue;

public interface VenueService {
    List<Venue> findAll();
    Venue findById(Long id);
    Venue save(Venue venue);
    void deleteById(Long id);
}