package com.riwi.history3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.history3.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Búsqueda por nombre insensible a mayúsculas (para el motor de búsqueda)
    Category findByNameIgnoreCase(String name);
    
}
