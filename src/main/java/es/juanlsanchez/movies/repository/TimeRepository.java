package es.juanlsanchez.movies.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.juanlsanchez.movies.domain.Cinema;
import es.juanlsanchez.movies.domain.Movie;
import es.juanlsanchez.movies.domain.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

  public Optional<Time> findOneByInstantAndCinemaAndMovie(Instant instant, Cinema cinema,
      Movie movie);

}
