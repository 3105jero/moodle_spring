package com.riwi.history3.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.riwi.history3.dto.EventSummaryDTO;
import com.riwi.history3.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Listado principal — usa el DTO para evitar cargar entidades pesadas
    // NEW keyword en JPQL llama al constructor del Record directamente
    @Query("""
        SELECT new com.riwi.history3.dto.EventSummaryDTO(
            e.id, e.name, e.eventDate, v.name, v.city
        )
        FROM Event e
        JOIN e.venue v
        ORDER BY e.eventDate DESC
        """)
    Slice<EventSummaryDTO> findAllSummaries(Pageable pageable);

    // Búsqueda por ciudad — LOWER() hace la búsqueda insensible a mayúsculas
    @Query("""
        SELECT new com.riwi.history3.dto.EventSummaryDTO(
            e.id, e.name, e.eventDate, v.name, v.city
        )
        FROM Event e
        JOIN e.venue v
        WHERE LOWER(v.city) LIKE LOWER(CONCAT('%', :city, '%'))
        ORDER BY e.eventDate DESC
        """)
    Slice<EventSummaryDTO> findByCity(@Param("city") String city, Pageable pageable);

    // Búsqueda por nombre de categoría — insensible a mayúsculas
    @Query("""
        SELECT new com.riwi.history3.dto.EventSummaryDTO(
            e.id, e.name, e.eventDate, v.name, v.city
        )
        FROM Event e
        JOIN e.venue v
        JOIN e.categories c
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))
        ORDER BY e.eventDate DESC
        """)
    Slice<EventSummaryDTO> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    // Búsqueda por capacidad mínima del venue
    @Query("""
        SELECT new com.riwi.history3.dto.EventSummaryDTO(
            e.id, e.name, e.eventDate, v.name, v.city
        )
        FROM Event e
        JOIN e.venue v
        WHERE v.capacity >= :capacity
        ORDER BY e.eventDate DESC
        """)
    Slice<EventSummaryDTO> findByMinCapacity(@Param("capacity") Integer capacity, Pageable pageable);

    // Búsqueda por rango de fechas
    @Query("""
        SELECT new com.riwi.history3.dto.EventSummaryDTO(
            e.id, e.name, e.eventDate, v.name, v.city
        )
        FROM Event e
        JOIN e.venue v
        WHERE e.eventDate BETWEEN :start AND :end
        ORDER BY e.eventDate DESC
        """)
    Slice<EventSummaryDTO> findByDateBetween(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        Pageable pageable
    );

    // Carga completa de un evento con todas sus relaciones en UNA sola query
    // @EntityGraph resuelve el problema N+1 cargando venue y categories juntos
    @EntityGraph(attributePaths = {"venue", "categories"})
    @Query("SELECT e FROM Event e WHERE e.id = :id")
    java.util.Optional<Event> findByIdWithRelations(@Param("id") Long id);
}