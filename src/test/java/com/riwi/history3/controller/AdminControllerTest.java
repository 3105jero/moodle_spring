package com.riwi.history3.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.riwi.history3.dto.EventSummaryDTO;
import com.riwi.history3.model.Category;
import com.riwi.history3.model.Venue;
import com.riwi.history3.service.CategoryService;
import com.riwi.history3.service.EventService;
import com.riwi.history3.service.VenueService;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private VenueService venueService;

    @MockitoBean
    private CategoryService categoryService;

    // ── Test 1: dashboard carga correctamente sin filtros ────────
    @Test
    void dashboard_sinFiltros_retornaVistaConEventos() throws Exception {

        // Datos de prueba — un EventSummaryDTO simulado
        EventSummaryDTO dto = new EventSummaryDTO(
                1L,
                "Concierto de Jazz",
                LocalDateTime.of(2025, 6, 15, 20, 0),
                "Teatro Mayor",
                "Bogotá"
        );

        // Mock del servicio — retorna un Slice con el DTO
        when(eventService.findAllSummaries(any(PageRequest.class)))
                .thenReturn(new SliceImpl<>(List.of(dto)));

        when(venueService.findAll())
               .thenReturn(List.of(Venue.builder()
        .id(1L)
        .name("Teatro Mayor")
        .address("Calle 24")
        .city("Bogotá")
        .capacity(2000)
        .build()));

        when(categoryService.findAll())
                .thenReturn(List.of(new Category(1L, "Conciertos", "Eventos musicales")));
        
        // Ejecuta GET /admin y verifica la respuesta
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("venues"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("hasPrevious", false));
    }

    // ── Test 2: dashboard filtra por ciudad ──────────────────────
    @Test
    void dashboard_conFiltroCiudad_retornaEventosFiltrados() throws Exception {

        EventSummaryDTO dto = new EventSummaryDTO(
                2L,
                "Festival Gastronómico",
                LocalDateTime.of(2025, 7, 20, 12, 0),
                "Plaza Mayor",
                "Medellín"
        );

        // Mock específico para búsqueda por ciudad
        when(eventService.findByCity(eq("Medellín"), any(PageRequest.class)))
                .thenReturn(new SliceImpl<>(List.of(dto)));

        when(venueService.findAll()).thenReturn(List.of());
        when(categoryService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/admin").param("city", "Medellín"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attribute("cityFilter", "Medellín"));
    }

    // ── Test 3: dashboard filtra por categoría ───────────────────
    @Test
    void dashboard_conFiltroCategoria_retornaEventosFiltrados() throws Exception {

        EventSummaryDTO dto = new EventSummaryDTO(
                3L,
                "Concierto de Rock",
                LocalDateTime.of(2025, 8, 10, 21, 0),
                "Plaza Mayor",
                "Medellín"
        );

        when(eventService.findByCategoryName(eq("Conciertos"), any(PageRequest.class)))
                .thenReturn(new SliceImpl<>(List.of(dto)));

        when(venueService.findAll()).thenReturn(List.of());
        when(categoryService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/admin").param("categoryName", "Conciertos"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attribute("categoryFilter", "Conciertos"));
    }
}