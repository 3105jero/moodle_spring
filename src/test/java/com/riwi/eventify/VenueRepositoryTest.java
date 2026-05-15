package com.riwi.eventify;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;

@DataJpaTest
class VenueRepositoryTest {

    @Autowired
    private VenueRepository venueRepository;

    @BeforeEach
    void setUp() {
        venueRepository.deleteAll();
        venueRepository.save(new Venue(null, "Teatro Nacional", "Calle 26 #5-60, Bogotá", 500));
        venueRepository.save(new Venue(null, "Parque Simón Bolívar", "Bogotá", 50000));
        venueRepository.save(new Venue(null, "Estadio El Campín", "Bogotá", 36000));
    }

    @Test
    void shouldPersistAndRetrieveVenue() {
        Venue saved = venueRepository.save(new Venue(null, "Centro de Convenciones", "Medellín", 3000));

        Optional<Venue> found = venueRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Centro de Convenciones");
    }

    @Test
    void shouldFindVenuesByNameContaining() {
        List<Venue> results = venueRepository.findByNameContainingIgnoreCase("teatro");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Teatro Nacional");
    }

    @Test
    void shouldReturnPagedResults() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("name").ascending());

        Page<Venue> page = venueRepository.findAll(pageable);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }

    @Test
    void shouldDeleteVenueById() {
        Venue venue = venueRepository.findAll().get(0);
        Long id = venue.getId();

        venueRepository.deleteById(id);

        assertThat(venueRepository.findById(id)).isEmpty();
    }

    @Test
    void shouldReturnEmptyForNonExistentId() {
        Optional<Venue> result = venueRepository.findById(9999L);

        assertThat(result).isEmpty();
    }
}