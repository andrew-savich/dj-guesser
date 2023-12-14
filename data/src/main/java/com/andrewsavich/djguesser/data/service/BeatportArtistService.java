package com.andrewsavich.djguesser.data.service;

import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import com.andrewsavich.djguesser.data.entity.Genre;

import java.util.List;

public interface BeatportArtistService {
    List<BeatportArtist> getAll();
    List<BeatportArtist> getAllByGenreId(Long genreId);
    BeatportArtist getById(Long id);
    void create(BeatportArtist beatportArtist);
    void update(BeatportArtist beatportArtist);
    void delete(BeatportArtist beatportArtist);
    void deleteById(Long id);

}
