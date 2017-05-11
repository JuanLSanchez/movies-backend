package es.juanlsanchez.movies.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.juanlsanchez.movies.domain.Cinema;
import es.juanlsanchez.movies.repository.CinemaRepository;
import es.juanlsanchez.movies.service.CinemaService;

@Service
public class DefaultCinemaService implements CinemaService {

  private final CinemaRepository cinemaRepository;

  public DefaultCinemaService(final CinemaRepository cinemaRepository) {
    this.cinemaRepository = cinemaRepository;
  }

  @Override
  public Optional<Cinema> findOneByMaxCode() {
    return cinemaRepository.findTopByOrderByCodeDesc();
  }

  @Override
  public Cinema create(Cinema cinema) {
    return this.cinemaRepository.save(cinema);
  }

  @Override
  public List<Cinema> findAll() {
    return this.cinemaRepository.findAll();
  }

}
