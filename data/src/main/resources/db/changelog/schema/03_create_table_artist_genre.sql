CREATE TABLE IF NOT EXISTS artist_genre(
    artist_id int REFERENCES beatport_artists(id),
    genre_id int REFERENCES genres(id),
    PRIMARY KEY(artist_id, genre_id)
);