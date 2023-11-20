package com.andrewsavich.djguesser.data.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "beatport_artists")
public class BeatportArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nickname")
    private String nickname;
    @Column(name = "page_url")
    private String pageUrl;
    @Column(name = "photo_url")
    private String photoUrl;

    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "artist_genre", joinColumns = @JoinColumn(name = "artist_id"))
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BeatportArtist() {
        this.genres = new HashSet<>();
    }

    public BeatportArtist(String nickname, String pageUrl, String photoUrl, Set<Genre> genres) {
        this.nickname = nickname;
        this.pageUrl = pageUrl;
        this.photoUrl = photoUrl;
        this.genres = genres;
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

    @Override
    public String toString() {
        return "BeatportArtist{" +
                "nickname='" + nickname + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", genres=" + genres +
                '}';
    }
}
