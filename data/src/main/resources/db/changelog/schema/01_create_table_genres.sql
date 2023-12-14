CREATE TABLE IF NOT EXISTS genres (
	id serial PRIMARY KEY,
	genre_name varchar(100) NOT NULL UNIQUE,
	genre_link varchar(500)
);