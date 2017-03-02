package es.juanlsanchez.movies.config.property;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "app.compraentradas")
@Getter
@Setter
@Validated
public class CompraentradaProperty {

  @NotNull
  public String urlToListMovies;
  @NotNull
  public String cssQueryToListMovies;

}
