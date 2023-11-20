package com.andrewsavich.djguesser.beatportscraper.service;


import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import com.andrewsavich.djguesser.data.entity.Genre;

import java.util.List;
import java.util.Map;

public interface ScraperService {
    Map<String, BeatportArtist> scrapeAllArtistsByGenres(List<Genre> genres);

//    String scrapePhotoUrlByArtist(BeatportArtist artist);
}
