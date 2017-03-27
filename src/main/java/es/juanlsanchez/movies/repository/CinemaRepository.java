package es.juanlsanchez.movies.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.juanlsanchez.movies.domain.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

  public Optional<Cinema> findTopByOrderByCodeDesc();

}
