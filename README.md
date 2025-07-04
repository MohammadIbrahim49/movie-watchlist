#  Movie Watchlist Application

A Spring Boot RESTful API that allows users to search for movies by title using the OMDb and TMDb APIs, automatically download posters, and manage a personal watchlist. Supports CRUD operations and image downloads.



##  Features

-  Fetch movies by title (OMDb + TMDb integration)
- ️ Auto-download movie poster images
-  Store movie info: title, release year, genre, director, image, etc.
-  Mark movies as watched
-  Rate movies from 0–10
-  Paginated movie list
-  Persistent H2 Database (`./data/movie-db.mv.db`)



##  Tech Stack

- Java 17+ / OpenJDK 23
- Spring Boot 3
- Spring Data JPA
- OMDb API
- TMDb API
- H2 Database
- Maven



##  Getting Started
### 1. Clone the Repository
```bash
git https://appliedmath.toi.inholland.nl/mibrahim/oop3_Assignment_Ibrahim.git
cd movie-watchlist-app
```



### 2. Build & Run the App


./mvnw spring-boot:run
```

Or in IntelliJ:

- Open the project
- Run `MovieWatchlistApplication.java`

Server will start at: `http://localhost:8080`



##  API Endpoints

| Method | Endpoint                         | Description                    |
|--------|----------------------------------|--------------------------------|
| POST   | `/api/movies/fetch?title=...`    | Fetch and save a movie         |
| GET    | `/api/movies`                    | Get all movies (paginated)     |
| GET    | `/api/movies/{id}`               | Get a movie by ID              |
| PUT    | `/api/movies/{id}/watched`       | Update watched flag            |
| PUT    | `/api/movies/{id}/rating`        | Update movie rating            |
| DELETE | `/api/movies/{id}`               | Delete movie                   |



##  Testing with Postman

### 1. Fetch a Movie

- Method: `POST`
- URL: `http://localhost:8080/api/movies/fetch?title=Inception`

### 2. Update Rating

- Method: `PUT`
- URL: `http://localhost:8080/api/movies/1/rating`
- Body Type: `x-www-form-urlencoded`
- Body:
  

### 3. Update Watched Status

- Method: `PUT`
- URL: `http://localhost:8080/api/movies/1/watched`
- Body Type: `x-www-form-urlencoded`
- Body:
  ```
  watched=true
  ```



## H2 Database Access

To view the database in browser:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/movie-db`
- User: `sa`
- Password: *(leave blank)*



##  Image Storage

Downloaded movie poster images are saved in the `/images` directory at the root of the project.



##  API Keys

Store  API keys securely in `src/main/resources/application.properties`:

```properties
omdb.api.key=4dfc3708
tmdb.api.key=f4504e91fbc6d448554603d394c525c6
```

Then inject them in the service class using:

```java
@Value("${omdb.api.key}")
private String omdbApiKey;
```



## License

MIT License © 2025 Mohammad Ibrahim
<<<<<<< HEAD
##  Extra Features Implemented

- Unit tests using JUnit & Mockito
- Stream API used to filter & sort movies
- Multithreading (fetching APIs & downloading poster concurrently)

=======
>>>>>>> b7f5253a8ae8092c632f335faa05a18d33c424d6
