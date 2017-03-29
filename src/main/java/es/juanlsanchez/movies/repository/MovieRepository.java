package es.juanlsanchez.movies.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.juanlsanchez.movies.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  public Optional<Movie> findByTitle(String title);

}
