package com.riwi.eventify.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.EventRepository;
import com.riwi.eventify.repositories.VenueRepository;

@Configuration
public class DataSeeder {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public DataSeeder(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    // Solo inserta si la tabla está vacía → idempotente entre reinicios
    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (eventRepository.count() == 0) {
                eventRepository.save(new Event(null, "Concierto de Rock", "2026-12-01", "Gran concierto en vivo"));
                eventRepository.save(new Event(null, "Festival de Jazz", "2026-11-15", "Festival anual de jazz"));
            }
            if (venueRepository.count() == 0) {
                venueRepository.save(new Venue(null, "Teatro Nacional", "Calle 26 #5-60, Bogotá", 500));
                venueRepository.save(new Venue(null, "Parque Simón Bolívar", "Bogotá", 50000));
            }
        };
    }
}