package com.example.movie_watchlist.service;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class AsyncMovieFetcher {

    private final String OMDB_API_KEY = "4dfc3708";
    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<JSONObject> fetchOMDbData(String title) {
        String url = "http://www.omdbapi.com/?t=" + title + "&apikey=" + OMDB_API_KEY;
        JSONObject omdbData = new JSONObject(restTemplate.getForObject(url, String.class));
        return CompletableFuture.completedFuture(omdbData);
    }

    @Async
    public CompletableFuture<JSONObject> fetchTMDbData(TMDBClient tmdbClient, String title) {
        JSONObject tmdbData = tmdbClient.fetchMovieDetails(title);
        return CompletableFuture.completedFuture(tmdbData);
    }

    @Async
    public CompletableFuture<String> downloadPoster(TMDBClient tmdbClient, String posterPath) {
        String filePath = tmdbClient.downloadPoster(posterPath);
        return CompletableFuture.completedFuture(filePath);
    }
}
