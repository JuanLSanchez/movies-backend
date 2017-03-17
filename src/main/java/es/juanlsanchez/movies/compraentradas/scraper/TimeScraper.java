package es.juanlsanchez.movies.compraentradas.scraper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import es.juanlsanchez.movies.compraentradas.dto.TimeDTO;

public interface TimeScraper {

  public List<TimeDTO> findOneByDayAndCinemaAndMovie(LocalDate day, String cinemaCode,
      String movieCode) throws IOException;


}
