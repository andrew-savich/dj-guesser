package com.andrewsavich.djguesser.data.repository;

import com.andrewsavich.djguesser.data.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
