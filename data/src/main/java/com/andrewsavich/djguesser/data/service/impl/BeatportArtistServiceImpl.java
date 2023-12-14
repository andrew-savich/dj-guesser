package com.andrewsavich.djguesser.data.service.impl;

import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import com.andrewsavich.djguesser.data.entity.Genre;
import com.andrewsavich.djguesser.data.repository.BeatportArtistRepository;
import com.andrewsavich.djguesser.data.service.BeatportArtistService;
import com.andrewsavich.djguesser.data.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeatportArtistServiceImpl implements BeatportArtistService {
    private final BeatportArtistRepository beatportArtistRepository;
    private final GenreService genreService;

    public BeatportArtistServiceImpl(BeatportArtistRepository beatportArtistRepository, GenreService genreService) {
        this.beatportArtistRepository = beatportArtistRepository;
        this.genreService = genreService;
    }

    @Override
    public List<BeatportArtist> getAll() {
        return beatportArtistRepository.findAll();
    }

    public List<BeatportArtist> getAllByGenreId(Long genreId) {
        return beatportArtistRepository.findBeatportArtistByGenre(genreId);
    }

    @Override
    public BeatportArtist getById(Long id) {
        return beatportArtistRepository.findById(id).get();
    }

    @Override
    public void create(BeatportArtist beatportArtist) {
        beatportArtistRepository.save(beatportArtist);
    }

    @Override
    public void update(BeatportArtist beatportArtist) {
        BeatportArtist updatedArtist = beatportArtistRepository.findById(beatportArtist.getId()).get();
        updatedArtist.setGenres(beatportArtist.getGenres());
        updatedArtist.setNickname(beatportArtist.getNickname());
        updatedArtist.setPageUrl(beatportArtist.getPageUrl());
        updatedArtist.setPhotoUrl(beatportArtist.getPhotoUrl());

        beatportArtistRepository.save(updatedArtist);
    }

    @Override
    public void delete(BeatportArtist beatportArtist) {
        beatportArtistRepository.delete(beatportArtist);
    }

    @Override
    public void deleteById(Long id) {
        beatportArtistRepository.deleteById(id);
    }
}