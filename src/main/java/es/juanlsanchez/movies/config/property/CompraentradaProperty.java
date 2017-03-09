package es.juanlsanchez.movies.config.property;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app.compraentradas")
@Getter
@Setter
@Validated
public class CompraentradaProperty {

  @NotNull
  private String urlToListMovies;
  @NotNull
  private String urlToGetMovie;
  @NotNull
  private String urlToGetCinema;
  @NotNull
  private String cssQueryToListMovies;

}
