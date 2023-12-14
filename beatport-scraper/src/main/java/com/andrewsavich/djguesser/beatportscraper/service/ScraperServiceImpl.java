package com.andrewsavich.djguesser.beatportscraper.service;

import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import com.andrewsavich.djguesser.data.entity.Genre;
import com.andrewsavich.djguesser.data.service.BeatportArtistService;
import com.andrewsavich.djguesser.data.service.GenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScraperServiceImpl implements ScraperService {

    private final WebClient webClient;
    private final GenreService genreService;
    private final BeatportArtistService beatportArtistService;

    public ScraperServiceImpl(WebClient webClient, GenreService genreService, BeatportArtistService beatportArtistService) {
        this.webClient = webClient;
        this.genreService = genreService;
        this.beatportArtistService = beatportArtistService;
    }

    @Override
    public void scrapeArtistsByAllGenres() {
        List<Genre> genres = genreService.getAll();
        Map<String, BeatportArtist> beatportArtists = new HashMap<>();

        for (Genre genre : genres) {
            beatportArtists.putAll(scrapeAllArtistsByGenre(genre));
        }

        saveAllArtists(beatportArtists);
    }

    @Override
    public void scrapeArtistPhotos() {
        List<BeatportArtist> artists = beatportArtistService.getAll();
        if (!artists.isEmpty()) {
            for (BeatportArtist artist : artists) {
                if (artist.getPhotoUrl() == null && artist.getPageUrl() != null) {
                    artist.setPhotoUrl(getArtistPhotoURL(artist.getPageUrl()));
                    beatportArtistService.update(artist);
                }
            }
        }

    }

    private void saveAllArtists(Map<String, BeatportArtist> beatportArtists) {
        for (BeatportArtist artist : beatportArtists.values()) {
            System.out.println("Save to the db: " + artist);
            beatportArtistService.create(artist);
        }
    }

    private Map<String, BeatportArtist> scrapeAllArtistsByGenre(Genre genre) {
        System.out.println("--------Scraping for: " + genre.getName() + "   --------");
        Map<String, BeatportArtist> beatportArtists = new HashMap<>();
        String json = getJsonFromPage(genre.getLink());
        List<JsonNode> artistJsonNodes = getArtistJsonNodesFromJson(json);

        for (JsonNode node : artistJsonNodes) {
            String beatportId = node.findValue("id").asText();
            String name = node.findValue("name").asText();
            String slug = node.findValue("slug").asText();

            BeatportArtist artist = new BeatportArtist();
            artist.setBeatportId(beatportId);
            artist.setNickname(name);
            artist.setGenre(genre);
            artist.setPageUrl("https://www.beatport.com/artist/" + slug + "/" + beatportId);

            System.out.println("Putting to the map: " + artist);
            beatportArtists.put(name, artist);
        }

        return beatportArtists;
    }

    private String getArtistPhotoURL(String pageUrl) {
        String json = getJsonFromPage(pageUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }

        if (rootNode == null) {
            throw new RuntimeException("Root node is null");
        }

        JsonNode result = rootNode
                .path("props")
                .path("pageProps")
                .path("artist")
                .path("image")
                .path("uri");

        return result == null ? "" : result.asText();
    }

    private List<JsonNode> getArtistJsonNodesFromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }

        if (rootNode == null) {
            throw new RuntimeException("Root node is null");
        }

        JsonNode results = rootNode
                .path("props")
                .path("pageProps")
                .path("dehydratedState")
                .path("queries").get(0)
                .path("state")
                .path("data")
                .path("results");

        List<JsonNode> artistJsonNodes = results.findValues("artists");

        return artistJsonNodes;
    }

    private String getJsonFromPage(String pageURI) {
        String json = "";

        try {
            String retrievedDataFromPage = webClient.get().uri(new URI(pageURI)).retrieve().bodyToMono(String.class).block();
            Document doc = Jsoup.parse(retrievedDataFromPage);
            json = doc.select("script[id=\"__NEXT_DATA__\"]").get(0).data();
        } catch (URISyntaxException e) {
            System.out.println(e);
        }

        return json;
    }

}