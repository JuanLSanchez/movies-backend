package es.juanlsanchez.movies.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app.properties", ignoreUnknownFields = false)
public class AppConfigProperty {

  private final CorsConfiguration cors = new CorsConfiguration();

}
