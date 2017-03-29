package es.juanlsanchez.movies.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.juanlsanchez.movies.domain.Movie;
import es.juanlsanchez.movies.repository.MovieRepository;
import es.juanlsanchez.movies.service.MovieService;

@Service
public class DefaultMovieService implements MovieService {

  private final MovieRepository movieRepository;

  public DefaultMovieService(final MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public Movie save(Movie movie) {
    return movieRepository.save(movie);
  }

  @Override
  public Movie saveIfNotExistTitle(Movie movie) {
    Optional<Movie> optionalMovie = findOneByTitle(movie.getTitle());
    Movie result;
    if (optionalMovie.isPresent()) {
      result = optionalMovie.get();
    } else {
      result = save(movie);
    }
    return result;
  }

  @Override
  public Optional<Movie> findOneByTitle(String title) {
    return this.movieRepository.findByTitle(title);
  }

  @Override
  public Optional<Movie> findOneByMaxCode() {
    return movieRepository.findTopByOrderByCodeDesc();
  }

}
