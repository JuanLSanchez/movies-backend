package es.juanlsanchez.movies.compraentradas.task;

import java.io.IOException;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;
import es.juanlsanchez.movies.compraentradas.mapper.MovieDetailsDTOMapper;
import es.juanlsanchez.movies.compraentradas.scraper.MovieScraper;
import es.juanlsanchez.movies.domain.Movie;
import es.juanlsanchez.movies.service.MovieService;
import es.juanlsanchez.movies.task.Task;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
@ConditionalOnProperty("app.properties.task.movie")
@Profile("tasks")
public class MovieTask implements Task {

  private final MovieScraper movieScraper;
  private final MovieService movieService;
  private final MovieDetailsDTOMapper movieDetailsDTOMapper;

  public static final int MAX_ATTEMPTS = 30;

  public MovieTask(final MovieScraper movieScraper, final MovieService movieService,
      final MovieDetailsDTOMapper movieDetailsDTOMapper) {
    this.movieScraper = movieScraper;
    this.movieDetailsDTOMapper = movieDetailsDTOMapper;
    this.movieService = movieService;
  }

  @Profile("initializer")
  @EventListener(ContextRefreshedEvent.class)
  public void run() {
    log.info("Initializing movies...");
    int i = 0;
    Integer code = 0;
    int total = 0;

    while (i < MAX_ATTEMPTS) {
      code++;
      Optional<MovieDetailsDTO> movieDetailsDTO = findByCode(code);
      if (movieDetailsDTO.isPresent()) {
        Movie movie = movieDetailsDTOMapper.toDomainMovie(movieDetailsDTO.get());
        movieService.saveIfNotExistTitle(movie);
        log.debug("Save movie: {}", movie);
        total++;
      } else {
        i++;
      }
    }

    log.info("Initialized with {} movies", total);
  }

  private Optional<MovieDetailsDTO> findByCode(Integer code) {
    Optional<MovieDetailsDTO> result;
    try {
      result = movieScraper.findOne(code.toString());
    } catch (IOException e) {
      result = Optional.empty();
      log.error("Error to get the movie with code {}", code);
    }
    return result;
  }

}
