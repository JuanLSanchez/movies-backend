package es.juanlsanchez.movies.service;

import java.util.List;
import java.util.Optional;

import es.juanlsanchez.movies.domain.Cinema;

public interface CinemaService {

  public Optional<Cinema> findOneByMaxCode();

  public Cinema create(Cinema newCinema);

  public List<Cinema> findAll();

}
