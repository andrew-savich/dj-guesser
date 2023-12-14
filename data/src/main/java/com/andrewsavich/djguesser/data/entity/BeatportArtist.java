package com.andrewsavich.djguesser.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "beatport_artists")
public class BeatportArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beatport_id")
    private String beatportId;

    @Column(name = "nickname")
    private String nickname;
    @Column(name = "page_url")
    private String pageUrl;
    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToMany
    @JoinTable(
            name = "artist_genre",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeatportId() {
        return beatportId;
    }

    public void setBeatportId(String beatportId) {
        this.beatportId = beatportId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void setGenre(Genre genre) {
        this.genres.add(genre);
    }

    @Override
    public String toString() {
        return "BeatportArtist{" +
                "id=" + id +
                ", beatportId='" + beatportId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", genres=" + genres +
                '}';
    }
}
