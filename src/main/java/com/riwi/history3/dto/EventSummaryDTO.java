package com.riwi.history3.dto;

import java.time.LocalDateTime;

// Record de Java — inmutable, compacto, perfecto para proyecciones de solo lectura
// El orden de los parámetros debe coincidir EXACTAMENTE con el constructor en las queries JPQL
public record EventSummaryDTO(
    Long id,
    String eventName,
    LocalDateTime eventDate,
    String venueName,
    String city
) {
}