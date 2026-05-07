package com.riwi.eventify.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.riwi.eventify.models.Event;

@Repository
public class EventRepository {

    private List<Event> events = new ArrayList<>();

    public List<Event> findAll() {
        return events;
    }

    public Event save(Event event) {
        events.add(event);
        return event;
    }
}