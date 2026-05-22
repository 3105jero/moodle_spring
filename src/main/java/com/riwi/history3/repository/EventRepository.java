package com.riwi.history3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.history3.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}