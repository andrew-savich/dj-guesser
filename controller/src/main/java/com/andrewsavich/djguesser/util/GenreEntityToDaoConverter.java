package com.andrewsavich.djguesser.util;

import com.andrewsavich.djguesser.dao.GenreDAO;
import com.andrewsavich.djguesser.data.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreEntityToDaoConverter {
    public GenreDAO convert(Genre genre) {
        GenreDAO genreDAO = new GenreDAO();
        genreDAO.setId(genre.getId());
        genreDAO.setName(genre.getName());
        genreDAO.setLink(genre.getLink());

        return genreDAO;
    }
}
