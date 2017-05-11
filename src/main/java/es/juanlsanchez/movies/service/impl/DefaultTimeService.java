package es.juanlsanchez.movies.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.juanlsanchez.movies.domain.Time;
import es.juanlsanchez.movies.repository.TimeRepository;
import es.juanlsanchez.movies.service.TimeService;

@Service
public class DefaultTimeService implements TimeService {

  private final TimeRepository timeRepository;

  public DefaultTimeService(TimeRepository timeRepository) {
    this.timeRepository = timeRepository;
  }

  @Override
  public Time saveIfNotExist(Time time) {
    Time result;
    Optional<Time> optionalTime = timeRepository
        .findOneByInstantAndCinemaAndMovie(time.getInstant(), time.getCinema(), time.getMovie());
    if (optionalTime.isPresent()) {
      result = optionalTime.get();
    } else {
      result = save(time);
    }
    return result;
  }

  private Time save(Time time) {
    return this.timeRepository.save(time);
  }

}
