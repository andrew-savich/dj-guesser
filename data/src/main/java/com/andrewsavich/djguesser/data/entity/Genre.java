package com.andrewsavich.djguesser.data.entity;


import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genre_name")
    private String name;
    @Column(name = "genre_link")
    private String link;

    @ManyToMany(mappedBy = "genres")
    private Set<BeatportArtist> beatportArtists;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<BeatportArtist> getBeatportArtists() {
        return beatportArtists;
    }

    public void setBeatportArtists(Set<BeatportArtist> beatportArtists) {
        this.beatportArtists = beatportArtists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name) && Objects.equals(link, genre.link) && Objects.equals(beatportArtists, genre.beatportArtists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, link, beatportArtists);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
