package es.juanlsanchez.movies.scraper.compraentradas;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;
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
    assertThat(result.get(0).getCode()).isNotEmpty();
    assertThat(result.get(0).getHref()).isNotEmpty();
    assertThat(result.get(0).getTit()).isNotEmpty();
    assertThat(result.get(0).getTitle()).isNotEmpty();
  }

  @Test
  public void findOneTest() throws IOException {
    String code = "7678";
    Optional<MovieDetailsDTO> result = movieScraper.findOne(code);

    assertThat(result.isPresent());
    MovieDetailsDTO movie = result.get();
    assertThat(movie.getSrcImgPoster())
        .isEqualTo("http://www.compraentradas.com/Carteles/esportubien.jpg");
    assertThat(movie.getSrcImgLarge())
        .isEqualTo("http://www.compraentradas.com/ImagenGrande/esportubien.jpg");
    assertThat(movie.getDescription()).isEqualTo(
        "La peor pesadilla para un padre con una hija es que ésta crezca y llegue el día en el que le");
    assertThat(movie.getCode()).isEqualTo(code);
    assertThat(movie.getHref())
        .isEqualTo("http://www.compraentradas.com/CinesPelicula/7678/es-por-tu-bien");
    assertThat(movie.getTit()).isEqualTo("es-por-tu-bien");
    assertThat(movie.getTitle()).isEqualTo("ES POR TU BIEN");

  }

  @Test
  public void findOneNotExistsTest() throws IOException {
    String code = String.valueOf(Integer.MAX_VALUE);
    Optional<MovieDetailsDTO> result = movieScraper.findOne(code);

    assertThat(!result.isPresent());
  }

}
