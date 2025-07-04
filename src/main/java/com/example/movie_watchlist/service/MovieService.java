package com.example.movie_watchlist.service;

import com.example.movie_watchlist.model.Movie;
import com.example.movie_watchlist.repository.MovieRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MovieService {

    private final String OMDB_API_KEY = "4dfc3708";

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TMDBClient tmdbClient;

    @Autowired
    private AsyncMovieFetcher asyncMovieFetcher;

    public Movie fetchAndSaveMovie(String title) {
        try {
            //  parallel API calls
            CompletableFuture<JSONObject> omdbFuture = asyncMovieFetcher.fetchOMDbData(title);
            CompletableFuture<JSONObject> tmdbFuture = asyncMovieFetcher.fetchTMDbData(tmdbClient, title);

            JSONObject omdbData = omdbFuture.get();
            JSONObject tmdbData = tmdbFuture.get();

            if (tmdbData == null || !tmdbData.has("poster_path")) {
                throw new RuntimeException("Movie not found on TMDB");
            }

            CompletableFuture<String> posterFuture = asyncMovieFetcher.downloadPoster(tmdbClient, tmdbData.optString("poster_path"));

            String savedImageFile = posterFuture.get();

            Movie movie = new Movie();
            movie.setTitle(omdbData.optString("Title"));
            movie.setReleaseYear(omdbData.optString("Year"));
            movie.setDirector(omdbData.optString("Director"));
            movie.setGenre(omdbData.optString("Genre"));
            movie.setImagePath(savedImageFile);
            movie.setWatched(false);
            movie.setRating(0);

            return movieRepository.save(movie);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch and save movie: " + e.getMessage(), e);
        }
    }


    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie updateWatched(Long id, boolean watched) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setWatched(watched);
        return movieRepository.save(movie);
    }

    public Movie updateRating(Long id, int rating) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setRating(rating);
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<Movie> getUnwatchedSortedByYearDesc() {
        return movieRepository.findAll().stream()
                .filter(m -> !m.isWatched())
                .sorted(Comparator.comparing(Movie::getReleaseYear).reversed())
                .toList();
    }
}
