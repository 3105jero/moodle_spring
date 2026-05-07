package com.riwi.eventify.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.riwi.eventify.models.Venue;

@Repository
public class VenueRepository {

    private List<Venue> venues = new ArrayList<>();

    public List<Venue> findAll() {
        return venues;
    }

    public Venue save(Venue venue) {
        venues.add(venue);
        return venue;
    }
}