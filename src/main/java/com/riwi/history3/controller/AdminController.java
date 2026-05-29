package com.riwi.history3.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.riwi.history3.dto.EventSummaryDTO;
import com.riwi.history3.model.Event;
import com.riwi.history3.model.Venue;
import com.riwi.history3.service.CategoryService;
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
    private final CategoryService categoryService;

    // Tamaño fijo de página para el dashboard
    private static final int PAGE_SIZE = 10;

    // ── Panel principal ──────────────────────────────────────────
    @GetMapping
    public String dashboard(
            // Filtros opcionales — si no se envían, quedan null
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String categoryName,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        PageRequest pageable = PageRequest.of(page, PAGE_SIZE);
        Slice<EventSummaryDTO> slice;

        // Aplica el filtro correspondiente según los parámetros recibidos
        if (city != null && !city.isBlank()) {
            slice = eventService.findByCity(city, pageable);
        } else if (categoryName != null && !categoryName.isBlank()) {
            slice = eventService.findByCategoryName(categoryName, pageable);
        } else {
            slice = eventService.findAllSummaries(pageable);
        }

        model.addAttribute("events", slice.getContent());
        model.addAttribute("venues", venueService.findAll());
        model.addAttribute("categories", categoryService.findAll());

        // Datos de paginación para los botones Anterior/Siguiente
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", slice.hasNext());
        model.addAttribute("hasPrevious", page > 0);

        // Mantiene los filtros activos en el modelo para la URL
        model.addAttribute("cityFilter", city != null ? city : "");
        model.addAttribute("categoryFilter", categoryName != null ? categoryName : "");

        return "admin/dashboard";
    }

    // ── Formulario nuevo evento ──────────────────────────────────
    @GetMapping("/events/new")
    public String newEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("venues", venueService.findAll());
        // Pasa las categorías para los checkboxes del formulario
        model.addAttribute("categories", categoryService.findAll());
        return "admin/event-form";
    }

    // ── Guardar evento (POST-REDIRECT-GET) ───────────────────────
    @PostMapping("/events/save")
    public String saveEvent(
            @Valid @ModelAttribute("event") Event event,
            BindingResult result,
            // IDs de categorías seleccionadas en los checkboxes
            @RequestParam(required = false) java.util.List<Long> categoryIds,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("venues", venueService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            return "admin/event-form";
        }

        // Asigna las categorías seleccionadas al evento antes de guardar
        if (categoryIds != null) {
            categoryIds.forEach(id ->
                event.getCategories().add(categoryService.findById(id))
            );
        }

        eventService.save(event);
        return "redirect:/admin";
    }

    // ── Soft delete de evento ────────────────────────────────────
    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        // Borrado lógico — no elimina el registro físicamente
        eventService.softDelete(id);
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
    public String saveVenue(
            @Valid @ModelAttribute("venue") Venue venue,
            BindingResult result) {

        if (result.hasErrors()) {
            return "admin/venue-form";
        }
        venueService.save(venue);
        return "redirect:/admin";
    }
}