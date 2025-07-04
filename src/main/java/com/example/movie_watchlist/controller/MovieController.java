package com.example.movie_watchlist.controller;

import com.example.movie_watchlist.model.Movie;
import com.example.movie_watchlist.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    //  Fetch and save a movie by title
    @PostMapping("/fetch")
    public Movie fetchAndSaveMovie(@RequestParam String title) {
        return movieService.fetchAndSaveMovie(title);
    }

    //  Get all movies (paginated)
    @GetMapping
    public Page<Movie> getAllMovies(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieService.getAllMovies(pageable);
    }

    //  Get a movie by ID
    @GetMapping("/{id}")
    public Optional<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    //  Update the "watched" status
    @PutMapping("/{id}/watched")
    public Movie updateWatched(@PathVariable Long id, @RequestParam boolean watched) {
        return movieService.updateWatched(id, watched);
    }

    //  Update the rating
    @PutMapping("/{id}/rating")
    public Movie updateRating(@PathVariable Long id, @RequestParam int rating) {
        return movieService.updateRating(id, rating);
    }

    //  Delete a movie
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    //  Get unwatched movies sorted by year descending
    @GetMapping("/unwatched")
    public List<Movie> getUnwatchedSortedByYearDesc() {
        return movieService.getUnwatchedSortedByYearDesc();
    }
}
