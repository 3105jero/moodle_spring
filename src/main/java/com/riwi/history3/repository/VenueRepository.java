package com.riwi.history3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.history3.model.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}