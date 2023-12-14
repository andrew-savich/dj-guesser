package com.andrewsavich.djguesser.controller;

import com.andrewsavich.djguesser.beatportscraper.service.ScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class ScraperController {
    private final ScraperService scraperService;

    public ScraperController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @PostMapping("/scrape")
    public ResponseEntity<String> scrapeArtistsByAllGenres() {
        scraperService.scrapeArtistsByAllGenres();
        return ResponseEntity.ok("Scraping started successfully");
    }

    @PutMapping("/scrape/photos")
    public ResponseEntity<String> scrapeArtistsArtistPhotos() {
        scraperService.scrapeArtistPhotos();
        return ResponseEntity.ok("Scraping started successfully");
    }

}
