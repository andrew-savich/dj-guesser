package com.andrewsavich.djguesser.controller;

import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import com.andrewsavich.djguesser.data.service.BeatportArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BeatportArtistController {
    private final BeatportArtistService beatportArtistService;

    public BeatportArtistController(BeatportArtistService beatportArtistService) {
        this.beatportArtistService = beatportArtistService;
    }

    @GetMapping("/artists")
    public ResponseEntity<List<BeatportArtist>> getAllArtists() {
        return ResponseEntity.ok(beatportArtistService.getAll());
    }

    @GetMapping("/artists/genres/{id}")
    public ResponseEntity<List<BeatportArtist>> getAllArtistsByGenreId(@PathVariable("id") Long id) {
        List<BeatportArtist> beatportArtists = beatportArtistService.getAllByGenreId(id);
        System.out.println(beatportArtists.get(0));
        return ResponseEntity.ok(beatportArtists);
    }
}
