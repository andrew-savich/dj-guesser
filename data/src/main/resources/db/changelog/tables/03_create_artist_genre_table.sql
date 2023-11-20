CREATE TABLE artist_genre (
  artist_id INTEGER NOT NULL,
  genre_id INTEGER NOT NULL,
  PRIMARY KEY (artist_id, genre_id),
  FOREIGN KEY (artist_id) REFERENCES beatport_artists(id),
  FOREIGN KEY (genre_id) REFERENCES genres(id)
);