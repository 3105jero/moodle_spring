package com.riwi.history3.controller;

import com.riwi.history3.model.Event;
import com.riwi.history3.model.Venue;
import com.riwi.history3.service.EventService;
import com.riwi.history3.service.VenueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private VenueService venueService;

    @Test
    @DisplayName("Escenario 1 — Dashboard renderiza tabla con eventos y lugares")
    void dashboard_conDatos_retornaVista() throws Exception {

        Venue venue = Venue.builder()
                .id(1L)
                .name("Teatro Nacional")
                .city("Bogotá")
                .address("Calle 10 #5-23")
                .capacity(500)
                .build();

        Event event = Event.builder()
                .id(1L)
                .name("Concierto de Jazz")
                .description("Una noche de jazz en vivo")
                .eventDate(LocalDateTime.now().plusDays(5))
                .venue(venue)
                .build();

        when(eventService.findAll()).thenReturn(List.of(event));
        when(venueService.findAll()).thenReturn(List.of(venue));

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("venues"));
    }

    @Test
    @DisplayName("Escenario 2 — Dashboard muestra estado vacío cuando no hay registros")
    void dashboard_sinDatos_retornaVistaVacia() throws Exception {

        when(eventService.findAll()).thenReturn(Collections.emptyList());
        when(venueService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attribute("events", Collections.emptyList()));
    }

    @Test
    @DisplayName("Escenario 3 — Formulario de nuevo evento carga correctamente")
    void newEventForm_retornaFormulario() throws Exception {

        when(venueService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/events/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/event-form"))
                .andExpect(model().attributeExists("event"))
                .andExpect(model().attributeExists("venues"));
    }

    @Test
    @DisplayName("Escenario 4 — Formulario de nuevo lugar carga correctamente")
    void newVenueForm_retornaFormulario() throws Exception {

        mockMvc.perform(get("/admin/venues/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/venue-form"))
                .andExpect(model().attributeExists("venue"));
    }

    @Test
    @DisplayName("Escenario 5 — Guardar lugar válido redirige al dashboard")
    void saveVenue_valido_redirigeDashboard() throws Exception {

        mockMvc.perform(post("/admin/venues/save")
                        .param("name", "Teatro Nacional")
                        .param("city", "Bogotá")
                        .param("address", "Calle 10 #5-23")
                        .param("capacity", "500"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }
}