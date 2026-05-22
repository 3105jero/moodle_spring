package com.riwi.history3.service;

import java.util.List;

import com.riwi.history3.model.Event;

public interface EventService {
    List<Event> findAll();
    Event findById(Long id);
    Event save(Event event);
    void deleteById(Long id);
}