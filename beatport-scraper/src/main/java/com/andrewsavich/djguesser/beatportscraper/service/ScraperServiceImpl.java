package com.andrewsavich.djguesser.beatportscraper.service;

import com.andrewsavich.djguesser.data.entity.BeatportArtist;
import com.andrewsavich.djguesser.data.entity.Genre;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.andrewsavich.djguesser.beatportscraper.util.Constants.A_HREF_TO_ARTIST;
import static com.andrewsavich.djguesser.beatportscraper.util.Constants.HREF_ATTRIBUTE;
import static com.andrewsavich.djguesser.beatportscraper.util.Constants.MAIN_TAG;

//@Service
public class ScraperServiceImpl implements ScraperService {

    private final Map<String, BeatportArtist> beatportArtists;
    private final Duration duration;

    public ScraperServiceImpl(@Value("${duration}") long seconds) {
        this.beatportArtists = new HashMap<>();
        duration = Duration.ofSeconds(seconds);
    }

    @Override
    public Map<String, BeatportArtist> scrapeAllArtistsByGenres(List<Genre> genres) {
        if (genres.isEmpty()) {
            throw new RuntimeException("Genres are empty");
        }

        for (Genre genre : genres) {
            WebDriver driver = new ChromeDriver();
            driver.get(genre.getLink());

            WebDriverWait wait = new WebDriverWait(driver, duration);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(MAIN_TAG)));

            List<WebElement> elementsWithAttribute = driver.findElements(By.cssSelector(A_HREF_TO_ARTIST));
            for (WebElement element : elementsWithAttribute) {
                driver.findElements(By.cssSelector(A_HREF_TO_ARTIST));
                String name = element.getText();
                if (!name.isEmpty()) {
                    BeatportArtist artist = beatportArtists.get(name);

                    if (artist == null) {
                        artist = new BeatportArtist();
                        artist.setNickname(name);
                        artist.setPageUrl(element.getAttribute(HREF_ATTRIBUTE));
                        beatportArtists.put(name, artist);
                    }

                    artist.getGenres().add(genre);
                }
            }
            driver.quit();
        }

        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        List<CompletableFuture<BeatportArtist>> tasks = beatportArtists.entrySet().stream()
                .map(entry -> scrapePhotoUrlByArtist(entry.getValue(), executorService))
                .collect(Collectors.toList());

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        executorService.shutdown();

        return new HashMap<>(beatportArtists);
    }

    private void scrapePhotoUrlByArtist(BeatportArtist artist) {
        if (artist.getPageUrl().isEmpty()) {
            throw new RuntimeException("Page URL is empty");
        }
        WebDriver driver = new ChromeDriver();
        driver.get(artist.getPageUrl());
        WebDriverWait wait = new WebDriverWait(driver, duration);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(MAIN_TAG)));

        WebElement imgElementUsingCssSelector = driver.findElement(By.cssSelector("img[alt='" + artist.getNickname() + "']"));
        String src = imgElementUsingCssSelector.getAttribute("src");
        driver.quit();
        System.out.println("URL for: " + artist.getNickname() + ": " + src);

        artist.setPhotoUrl(src);
    }

    private CompletableFuture<BeatportArtist> scrapePhotoUrlByArtist(BeatportArtist artist, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> {
            if (artist.getPageUrl().isEmpty()) {
                throw new RuntimeException("Page URL is empty");
            }
            WebDriver driver = new ChromeDriver();
            driver.get(artist.getPageUrl());
            WebDriverWait wait = new WebDriverWait(driver, duration);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(MAIN_TAG)));

            WebElement imgElementUsingCssSelector = driver.findElement(By.cssSelector("img[alt='" + artist.getNickname() + "']"));
            String src = imgElementUsingCssSelector.getAttribute("src");
            driver.quit();
            System.out.println("URL for: " + artist.getNickname() + ": " + src);

            artist.setPhotoUrl(src);
            return artist;
        }, executorService);
    }
}
