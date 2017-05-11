package es.juanlsanchez.movies.service;

import es.juanlsanchez.movies.domain.Time;

public interface TimeService {

  public Time saveIfNotExist(Time time);

}
