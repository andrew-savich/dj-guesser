package com.andrewsavich.djguesser.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
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
    private Set<BeatportArtist> beatportArtists = new HashSet<>();

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
}
