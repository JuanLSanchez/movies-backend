package es.juanlsanchez.movies.config;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import es.juanlsanchez.movies.config.property.AppConfigProperty;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CORSConfiguration {

  @Inject
  private AppConfigProperty appConfig;


  @Bean
  @ConditionalOnProperty(name = "app.properties.cors.allowed-origins")
  public CorsFilter corsFilter() {
    log.debug("Registering CORS filter");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = appConfig.getCors();
    source.registerCorsConfiguration("/api/**", config);
    return new CorsFilter(source);
  }

}
