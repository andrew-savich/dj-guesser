package com.andrewsavich.djguesser.data.service;

import com.andrewsavich.djguesser.data.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();
    Genre getById(Long id);
    void create(Genre genre);
    void update(Genre genre);
    void delete(Genre genre);
    void deleteById(Long id);
}
