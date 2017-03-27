package es.juanlsanchez.movies.compraentradas.task;

import java.io.IOException;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import es.juanlsanchez.movies.compraentradas.dto.CinemaDTO;
import es.juanlsanchez.movies.compraentradas.mapper.CinemaDTOMapper;
import es.juanlsanchez.movies.compraentradas.scraper.CinemaScraper;
import es.juanlsanchez.movies.domain.Cinema;
import es.juanlsanchez.movies.service.CinemaService;
import es.juanlsanchez.movies.task.Task;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
@ConditionalOnProperty("app.properties.task.cinema")
@Profile("tasks")
public class CinemaTask implements Task {

  private final CinemaScraper cinemaScraper;
  private final CinemaService cinemaService;
  private final CinemaDTOMapper cinemaDTOMapper;

  public static final int MAX_ATTEMPTS = 30;

  public CinemaTask(final CinemaScraper cinemaScraper, final CinemaService cinemaService,
      final CinemaDTOMapper cinemaDTOMapper) {
    this.cinemaScraper = cinemaScraper;
    this.cinemaService = cinemaService;
    this.cinemaDTOMapper = cinemaDTOMapper;
  }

  @Override
  @Scheduled(cron = "${app.properties.task.cinema}")
  public void run() {
    log.info("Run cinema task");
    Integer code = getLastCode();
    int i = 0;
    log.info("Start with code {}", code);

    while (i < MAX_ATTEMPTS) {
      code++;
      Optional<CinemaDTO> cinemaDTO = findCinema(code);
      if (cinemaDTO.isPresent()) {
        Cinema newCinema = cinemaDTOMapper.toDomainCinema(cinemaDTO.get());
        cinemaService.create(newCinema);
        log.debug("Saved cinema: {}", newCinema);
        i = 0;
      } else {
        i++;
      }
    }
    log.info("Finished with code {}", code);
  }

  private Optional<CinemaDTO> findCinema(Integer code) {
    Optional<CinemaDTO> result;
    try {
      result = cinemaScraper.findOne(String.format("%03d", code));
    } catch (IOException e) {
      result = Optional.empty();
      log.error("Error to get the cinema with code {}", code, e);
    }
    return result;
  }

  private Integer getLastCode() {
    Optional<Cinema> lastCinema = cinemaService.findOneByMaxCode();
    Integer code = 0;
    if (lastCinema.isPresent()) {
      code = Integer.valueOf(lastCinema.get().getCode());
    }
    return code;
  }

}
