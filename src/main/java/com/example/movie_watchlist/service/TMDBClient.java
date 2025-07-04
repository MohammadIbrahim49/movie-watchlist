package com.example.movie_watchlist.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

@Component
public class TMDBClient {

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private final String TMDB_API_URL = "https://api.themoviedb.org/3";
    private final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject fetchMovieDetails(String title) {
        try {
            String searchUrl = TMDB_API_URL + "/search/movie?query=" + title + "&api_key=" + tmdbApiKey;
            JSONObject response = new JSONObject(restTemplate.getForObject(searchUrl, String.class));
            JSONArray results = response.getJSONArray("results");

            if (results.isEmpty()) return null;
            return results.getJSONObject(0); // Return first match
        } catch (Exception e) {
            return null;
        }
    }

    public String downloadPoster(String posterPath) {
        try {
            if (posterPath == null || posterPath.isEmpty()) return null;

            String fullImageUrl = TMDB_IMAGE_BASE_URL + posterPath;
            String filename = "movie_" + posterPath.replace("/", "") + ".jpg";

            try (InputStream in = new URL(fullImageUrl).openStream();
                 FileOutputStream out = new FileOutputStream("images/" + filename)) {
                in.transferTo(out);
            }
            return filename;

        } catch (Exception e) {
            System.err.println("Failed to download image: " + e.getMessage());
            return null;
        }
    }
}
