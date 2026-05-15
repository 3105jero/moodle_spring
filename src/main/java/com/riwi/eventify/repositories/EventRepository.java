package com.riwi.eventify.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.eventify.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Derived Query: busca eventos cuyo nombre contenga el texto (case-insensitive)
    List<Event> findByNameContainingIgnoreCase(String name);

    // Versión paginada
    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);
}