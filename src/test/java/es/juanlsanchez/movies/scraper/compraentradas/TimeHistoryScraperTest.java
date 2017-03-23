package es.juanlsanchez.movies.scraper.compraentradas;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;
import es.juanlsanchez.movies.compraentradas.dto.TimeDTO;
import es.juanlsanchez.movies.compraentradas.dto.TimeHistoryDTO;
import es.juanlsanchez.movies.compraentradas.scraper.MovieScraper;
import es.juanlsanchez.movies.compraentradas.scraper.TimeHistoryScraper;
import es.juanlsanchez.movies.compraentradas.scraper.TimeScraper;
import es.juanlsanchez.movies.config.constants.SpringProfileConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({SpringProfileConstants.DEV, SpringProfileConstants.MYSQL})
public class TimeHistoryScraperTest {

  @Inject
  private TimeScraper timeScraper;
  @Inject
  private MovieScraper movieScraper;
  @Inject
  private TimeHistoryScraper timeHistoryScraper;

  @Test
  public void findOneByDayAndCinemaAndMovieTest() throws IOException {
    String cinemaCode = "007";
    List<MovieListDTO> movies = movieScraper.findAllActualFromCinema(cinemaCode);
    List<TimeDTO> times = this.timeScraper.findOneByDayAndCinemaAndMovie(
        LocalDate.now().plusDays(1L), cinemaCode, movies.get(0).getCode());
    TimeHistoryDTO result = timeHistoryScraper.findOneByTimeDTO(times.get(0));

    assertThat(result).isNotNull();
    assertThat(result.getNumberOfTotalSeat()).isGreaterThan(0);
    assertThat(result.getNumberOfTotalSeat()).isGreaterThanOrEqualTo(result.getNumberOfTotalSeat());
    assertThat(result.getInstant()).isLessThanOrEqualTo(Instant.now());
    assertThat(result.getSeats()).isNotEmpty();

  }

}
