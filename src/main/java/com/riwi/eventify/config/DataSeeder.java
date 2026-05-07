package com.riwi.eventify.config;

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

    // Inyección por constructor
    public DataSeeder(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

@Bean
public String loadData() {
    // Datos iniciales de eventos
    eventRepository.save(new Event(1L, "Concierto de Rock", "2026-12-01", "Gran concierto en vivo"));
    eventRepository.save(new Event(2L, "Festival de Jazz", "2026-11-15", "Festival anual de jazz"));

    // Datos iniciales de lugares
    venueRepository.save(new Venue(1L, "Teatro Nacional", "Calle 26 #5-60, Bogotá", 500));
    venueRepository.save(new Venue(2L, "Parque Simón Bolívar", "Bogotá", 50000));

    return "Datos cargados correctamente";
}
}