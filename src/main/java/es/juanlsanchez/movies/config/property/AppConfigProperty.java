package es.juanlsanchez.movies.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app.properties", ignoreUnknownFields = false)
public class AppConfigProperty {

  private final Task task = new Task();
  private final CorsConfiguration cors = new CorsConfiguration();

  @Getter
  @Setter
  public class Task {

    private String cinema;
    private String movie;
    private String time;
    private String timeHistory;

  }

}
