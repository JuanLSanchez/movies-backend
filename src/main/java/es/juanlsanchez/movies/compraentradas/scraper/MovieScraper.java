package es.juanlsanchez.movies.compraentradas.scraper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;
import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;

public interface MovieScraper {

  public List<MovieListDTO> findAllActual() throws IOException;

  public Optional<MovieDetailsDTO> findOneByMovieListDTO(MovieListDTO movieListDTO)
      throws IOException;

  public Optional<MovieDetailsDTO> findOne(String code) throws IOException;

  public List<MovieListDTO> findAllActualFromCinema(String cinmaCode) throws IOException;

}
