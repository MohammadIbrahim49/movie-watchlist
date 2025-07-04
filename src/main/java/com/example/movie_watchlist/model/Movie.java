package com.example.movie_watchlist.model;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String releaseYear;
    private String director;
    private String genre;
    private String imagePath;
    private boolean watched;
    private int rating;

    public Movie() {}

    public Movie(String title, String releaseYear, String director, String genre, String imagePath, boolean watched, int rating) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.genre = genre;
        this.imagePath = imagePath;
        this.watched = watched;
        this.rating = rating;
    }
}
