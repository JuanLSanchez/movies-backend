package es.juanlsanchez.movies.compraentradas.scraper;

import java.io.IOException;
import java.util.Optional;

import es.juanlsanchez.movies.compraentradas.dto.CinemaDTO;

public interface CinemaScraper {

  public Optional<CinemaDTO> findOne(String code) throws IOException;

}
