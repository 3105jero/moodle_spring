package com.riwi.history3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riwi.history3.model.Event;
import com.riwi.history3.model.Venue;
import com.riwi.history3.service.EventService;
import com.riwi.history3.service.VenueService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EventService eventService;
    private final VenueService venueService;

    // ── Panel principal ──────────────────────────────────────────
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("venues", venueService.findAll());
        return "admin/dashboard";
    }

    // ── Formulario nuevo evento ──────────────────────────────────
    @GetMapping("/events/new")
    public String newEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("venues", venueService.findAll());
        return "admin/event-form";
    }

    // ── Guardar evento (POST-REDIRECT-GET) ───────────────────────
    @PostMapping("/events/save")
    public String saveEvent(@Valid @ModelAttribute("event") Event event,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("venues", venueService.findAll());
            return "admin/event-form";
        }
        eventService.save(event);
        return "redirect:/admin";
    }

    // ── Formulario nuevo lugar ───────────────────────────────────
    @GetMapping("/venues/new")
    public String newVenueForm(Model model) {
        model.addAttribute("venue", new Venue());
        return "admin/venue-form";
    }

    // ── Guardar lugar (POST-REDIRECT-GET) ────────────────────────
    @PostMapping("/venues/save")
    public String saveVenue(@Valid @ModelAttribute("venue") Venue venue,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "admin/venue-form";
        }
        venueService.save(venue);
        return "redirect:/admin";
    }
}