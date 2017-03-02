package es.juanlsanchez.movies.scraper.compraentradas;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;
import es.juanlsanchez.movies.compraentradas.scraper.MovieScraper;
import es.juanlsanchez.movies.config.constants.SpringProfileConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({SpringProfileConstants.DEV, SpringProfileConstants.MYSQL})
public class MovieScraperTest {

  @Inject
  public MovieScraper movieScraper;

  @Test
  public void findAllActualTest() throws IOException {
    List<MovieListDTO> result = movieScraper.findAllActual();

    assertThat(result).isNotEmpty();
  }

}
