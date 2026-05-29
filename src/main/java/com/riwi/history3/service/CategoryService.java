package com.riwi.history3.service;

import java.util.List;

import com.riwi.history3.model.Category;

public interface CategoryService {

    // Lista completa — las categorías no se paginan, son pocas y estáticas
    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    void deleteById(Long id);
}