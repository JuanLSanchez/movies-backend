package es.juanlsanchez.movies.service;

import java.util.Optional;

import es.juanlsanchez.movies.domain.Movie;

public interface MovieService {

  public Movie save(Movie movie);

  public Movie saveIfNotExistTitle(Movie movie);

  public Optional<Movie> findOneByTitle(String title);

  public Optional<Movie> findOneByMaxCode();

}
