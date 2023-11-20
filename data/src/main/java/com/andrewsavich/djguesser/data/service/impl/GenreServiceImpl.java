package com.andrewsavich.djguesser.data.service.impl;

import com.andrewsavich.djguesser.data.entity.Genre;
import com.andrewsavich.djguesser.data.repository.GenreRepository;
import com.andrewsavich.djguesser.data.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(Long id) {
        return genreRepository.findById(id).get();
    }

    @Override
    public void create(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void update(Genre genre) {
        Genre updatedGenre = getById(genre.getId());
        updatedGenre.setBeatportArtists(genre.getBeatportArtists());
        updatedGenre.setLink(genre.getLink());
        updatedGenre.setName(genre.getName());

        genreRepository.save(updatedGenre);
    }

    @Override
    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }

    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
