package es.juanlsanchez.movies.compraentradas.task;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;
import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;
import es.juanlsanchez.movies.compraentradas.dto.TimeDTO;
import es.juanlsanchez.movies.compraentradas.mapper.MovieDetailsDTOMapper;
import es.juanlsanchez.movies.compraentradas.mapper.TimeDTOMapper;
import es.juanlsanchez.movies.compraentradas.scraper.MovieScraper;
import es.juanlsanchez.movies.compraentradas.scraper.TimeScraper;
import es.juanlsanchez.movies.domain.Cinema;
import es.juanlsanchez.movies.domain.Movie;
import es.juanlsanchez.movies.domain.Time;
import es.juanlsanchez.movies.service.CinemaService;
import es.juanlsanchez.movies.service.MovieService;
import es.juanlsanchez.movies.service.TimeService;
import es.juanlsanchez.movies.task.Task;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
@ConditionalOnProperty("app.properties.task.time")
@Profile("tasks")
public class TimeTask implements Task {

  private final CinemaService cinemaService;
  private final MovieScraper movieScraper;
  private final MovieService movieService;
  private final MovieDetailsDTOMapper movieDetailsDTOMapper;
  private final TimeScraper timeScraper;
  private final TimeService timeService;
  private final TimeDTOMapper timeDTOMapper;

  private static final int DAYS_TO_SCAN = 21;

  public TimeTask(final MovieScraper movieScraper, final CinemaService cinemaService,
      final MovieService movieService, final MovieDetailsDTOMapper movieDetailsDTOMapper,
      final TimeScraper timeScraper, final TimeService timeService,
      final TimeDTOMapper timeDTOMapper) {
    this.movieScraper = movieScraper;
    this.cinemaService = cinemaService;
    this.movieService = movieService;
    this.movieDetailsDTOMapper = movieDetailsDTOMapper;
    this.timeScraper = timeScraper;
    this.timeService = timeService;
    this.timeDTOMapper = timeDTOMapper;
  }

  @Override
  @EventListener(ContextRefreshedEvent.class)
  public void run() {
    log.debug("Run time task");
    List<Cinema> cinemas = cinemaService.findAll();
    log.debug("Found {} cinemas", cinemas.size());
    cinemas.parallelStream().forEach(cinema -> {
      try {
        findAllMovieByCinemaAndProcessTimes(cinema);
        log.info("Finished times for cinema {}", cinema);
      } catch (Exception e) {
        log.error("Error to get movies for cinema {}", cinema, e);
      }
    });
  }

  private void findAllMovieByCinemaAndProcessTimes(Cinema cinema) throws IOException {
    List<MovieListDTO> moviesFromCinema = movieScraper.findAllActualFromCinema(cinema.getCode());
    moviesFromCinema.parallelStream().forEach(movieListDTO -> {
      Optional<Movie> movie = findOneMovieOrCreate(movieListDTO);
      if (movie.isPresent()) {
        processAllTimesByMovieAndCinema(movie.get(), cinema);
      }
    });
  }

  private Optional<Movie> findOneMovieOrCreate(MovieListDTO movieListDTO) {
    Optional<Movie> result = this.movieService.findOneByTitle(movieListDTO.getTitle());
    // When is the first time
    if (!result.isPresent()) {
      try {
        Optional<MovieDetailsDTO> movieDetailsDTO =
            this.movieScraper.findOneByMovieListDTO(movieListDTO);
        if (movieDetailsDTO.isPresent()) {
          Movie movie = movieDetailsDTOMapper.toDomainMovie(movieDetailsDTO.get());
          result = Optional.of(this.movieService.saveIfNotExistTitle(movie));
          log.debug("Saved movie {}", result.get());
        }
      } catch (Exception e) {
        log.error("Error to get the movie {}", movieListDTO, e);
      }
    }
    return result;
  }

  private void processAllTimesByMovieAndCinema(Movie movie, Cinema cinema) {
    for (Integer i = 1; i <= DAYS_TO_SCAN; i++) {
      LocalDate day = LocalDate.now().plusDays(i.longValue());
      try {
        List<TimeDTO> times =
            timeScraper.findOneByDayAndCinemaAndMovie(day, cinema.getCode(), movie.getCode());
        for (TimeDTO timeDTO : times) {
          Time time = this.timeDTOMapper.toDomainTime(timeDTO);
          time.setCinema(cinema);
          time.setMovie(movie);
          timeService.saveIfNotExist(time);
          log.debug("Procesed time {}", time);
        }

      } catch (Exception e) {
        log.error("Error to get the times on {}, for the cinema {}, and movie {}", day, cinema,
            movie);
      }
    }
  }

}
