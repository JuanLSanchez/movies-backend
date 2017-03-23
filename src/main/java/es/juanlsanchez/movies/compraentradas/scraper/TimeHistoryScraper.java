package es.juanlsanchez.movies.compraentradas.scraper;

import java.io.IOException;

import es.juanlsanchez.movies.compraentradas.dto.TimeDTO;
import es.juanlsanchez.movies.compraentradas.dto.TimeHistoryDTO;

public interface TimeHistoryScraper {

  public TimeHistoryDTO findOneByTimeDTO(TimeDTO timeDTO) throws IOException;

}
