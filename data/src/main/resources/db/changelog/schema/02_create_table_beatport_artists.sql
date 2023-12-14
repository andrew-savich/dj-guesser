CREATE TABLE IF NOT EXISTS beatport_artists(
	id serial PRIMARY KEY,
	beatport_id varchar(50) UNIQUE,
	nickname varchar(100) NOT NULL UNIQUE,
	page_url varchar(500),
	photo_url varchar(500)
);