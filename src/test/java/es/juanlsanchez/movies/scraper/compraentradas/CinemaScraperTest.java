package es.juanlsanchez.movies.scraper.compraentradas;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.juanlsanchez.movies.compraentradas.dto.CinemaDTO;
import es.juanlsanchez.movies.compraentradas.scraper.CinemaScraper;
import es.juanlsanchez.movies.config.constants.SpringProfileConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({SpringProfileConstants.DEV, SpringProfileConstants.MYSQL})
public class CinemaScraperTest {

  @Inject
  public CinemaScraper cinemaScraper;

  @Test
  public void findOneTest() throws IOException {
    String code = "007";
    Optional<CinemaDTO> result = cinemaScraper.findOne(code);

    assertThat(result.isPresent());
    CinemaDTO cinema = result.get();
    assertThat(cinema.getAddress()).isEqualTo("Avd. España, s/n");
    assertThat(cinema.getCity()).isEqualTo("Dos Hermanas");
    assertThat(cinema.getCode()).isEqualTo(code);
    assertThat(cinema.getEmail()).isEqualTo("cineapolis_dh@siveentradas.com");
    assertThat(cinema.getHref())
        .isEqualTo("http://www.compraentradas.com/Cine/007/cineapolis-dos-hermanas-3d");
    assertThat(cinema.getName()).isEqualTo("Cineapolis Dos Hermanas 3D");
    assertThat(cinema.getNumberOfTheaters()).isEqualTo(9);
    assertThat(cinema.getPhone()).isEqualTo("955 675074");
    assertThat(cinema.getProvince()).isEqualTo("Sevilla");
    assertThat(cinema.getTit()).isEqualTo("cineapolis-dos-hermanas-3d");
  }


  @Test
  public void findOneNotExistTest() throws IOException {
    String code = "7";
    Optional<CinemaDTO> result = cinemaScraper.findOne(code);

    assertThat(!result.isPresent());
  }


}
