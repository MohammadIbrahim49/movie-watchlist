package com.example.movie_watchlist.service;

import com.example.movie_watchlist.model.Movie;
import com.example.movie_watchlist.repository.MovieRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private TMDBClient tmdbClient;

    @Mock
    private AsyncMovieFetcher asyncMovieFetcher;

    @InjectMocks
    private MovieService movieService;

    @Test
    public void testFetchAndSaveMovie_Success() throws Exception {
        // Arrange
        String title = "Inception";
        JSONObject omdbData = new JSONObject()
                .put("Title", "Inception")
                .put("Year", "2010")
                .put("Director", "Christopher Nolan")
                .put("Genre", "Action");

        JSONObject tmdbData = new JSONObject()
                .put("poster_path", "/poster.jpg");

        when(asyncMovieFetcher.fetchOMDbData(title)).thenReturn(CompletableFuture.completedFuture(omdbData));
        when(asyncMovieFetcher.fetchTMDbData(tmdbClient, title)).thenReturn(CompletableFuture.completedFuture(tmdbData));
        when(asyncMovieFetcher.downloadPoster(tmdbClient, "/poster.jpg")).thenReturn(CompletableFuture.completedFuture("saved.jpg"));

        Movie mockSaved = new Movie("Inception", "2010", "Christopher Nolan", "Action", "saved.jpg", false, 0);
        when(movieRepository.save(any(Movie.class))).thenReturn(mockSaved);

        // Act
        Movie result = movieService.fetchAndSaveMovie(title);

        // Assert
        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
        assertEquals("2010", result.getReleaseYear());
        assertEquals("saved.jpg", result.getImagePath());
    }

    @Test
    public void testGetUnwatchedSortedByYearDesc() {
        Movie m1 = new Movie("Movie A", "2020", "Director A", "Genre A", "img1.jpg", false, 0);
        Movie m2 = new Movie("Movie B", "2023", "Director B", "Genre B", "img2.jpg", false, 0);
        Movie m3 = new Movie("Movie C", "2022", "Director C", "Genre C", "img3.jpg", true, 0); // Watched

        when(movieRepository.findAll()).thenReturn(List.of(m1, m2, m3));

        List<Movie> result = movieService.getUnwatchedSortedByYearDesc();

        assertEquals(2, result.size());
        assertEquals("2023", result.get(0).getReleaseYear());
        assertEquals("2020", result.get(1).getReleaseYear());
    }
}
