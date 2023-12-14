package com.andrewsavich.djguesser.data.repository;

import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeatportArtistRepository extends JpaRepository<BeatportArtist, Long> {
    @Query(value =
            "SELECT * FROM beatport_artists ba " +
                    "INNER JOIN artist_genre ag ON ag.artist_id = ba.id " +
                    "WHERE ag.genre_id = :genreId", nativeQuery = true)
    List<BeatportArtist> findBeatportArtistByGenre(@Param("genreId") Long genreId);
}
