package com.andrewsavich.djguesser.controller;

import com.andrewsavich.djguesser.beatportscraper.service.ScraperService;
import com.andrewsavich.djguesser.beatportscraper.service.ScraperServiceImpl;
import com.andrewsavich.djguesser.dao.GenreDAO;
import com.andrewsavich.djguesser.data.entity.Genre;
import com.andrewsavich.djguesser.data.service.GenreService;
import com.andrewsavich.djguesser.util.GenreEntityToDaoConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GuesserController {

    private final GenreService genreService;
    private final GenreEntityToDaoConverter genreEntityToDaoConverter;

    public GuesserController(GenreService genreService, GenreEntityToDaoConverter genreEntityToDaoConverter) {
        this.genreService = genreService;
        this.genreEntityToDaoConverter = genreEntityToDaoConverter;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDAO>> getAllGenres() {
        List<Genre> genres = genreService.getAll();
        ScraperService scraperService = new ScraperServiceImpl(10L);
        scraperService.scrapeAllArtistsByGenres(genres);

        if (genres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<GenreDAO> genreDAOs = new ArrayList<>();
        for (Genre genre : genres) {
            genreDAOs.add(genreEntityToDaoConverter.convert(genre));
        }

        return ResponseEntity.ok(genreDAOs);
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<GenreDAO> getGenreById(@PathVariable("id") Long id) {
        Genre genre = genreService.getById(id);
        if (genre == null) {
            return ResponseEntity.notFound().build();
        }
        GenreDAO genreDAO = genreEntityToDaoConverter.convert(genre);

        return ResponseEntity.ok(genreDAO);
    }

//    @GetMapping("/scrap")
//    public void scrap() {
//        scraperService.scrapeAllArtistsByGenres(genreService.getAll());
//    }
}
