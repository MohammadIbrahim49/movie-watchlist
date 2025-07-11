package com.example.movie_watchlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync // Enables multithreading support


public class MovieWatchlistApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieWatchlistApplication.class, args);
    }
}
