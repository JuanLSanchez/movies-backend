package es.juanlsanchez.movies.config.property;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Component
@ConfigurationProperties(prefix = "app.compraentradas")
@Getter
@Setter
public class CompraentradaProperty {

  @NotNull
  private String urlToListMovies;
  @NotNull
  private String urlToGetMovie;
  @NotNull
  private String urlToGetCinema;
  @NotNull
  private String urlBase;
  @NotNull
  private String urlToGetTimes;
  @NotNull
  private String cssQueryToListMovies;

}
