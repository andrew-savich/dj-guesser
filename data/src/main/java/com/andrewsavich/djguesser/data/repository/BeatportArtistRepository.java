package com.andrewsavich.djguesser.data.repository;

import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeatportArtistRepository extends JpaRepository<BeatportArtist, Long> {
}
