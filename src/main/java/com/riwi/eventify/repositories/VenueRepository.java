package com.riwi.eventify.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.eventify.models.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    // Derived Query: busca lugares cuyo nombre contenga el texto (case-insensitive)
    List<Venue> findByNameContainingIgnoreCase(String name);

    // Versión paginada
    Page<Venue> findByNameContainingIgnoreCase(String name, Pageable pageable);
}