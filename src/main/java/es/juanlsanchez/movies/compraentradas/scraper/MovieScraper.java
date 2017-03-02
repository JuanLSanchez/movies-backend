package es.juanlsanchez.movies.compraentradas.scraper;

import java.io.IOException;
import java.util.List;

import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;

public interface MovieScraper {

  public List<MovieListDTO> findAllActual() throws IOException;

}
