package es.juanlsanchez.movies.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import es.juanlsanchez.movies.config.constants.SpringProfileConstants;

@Configuration
public class C3P0Config {
  /**
   * Constructor for ComboPooledDataSource bean.
   * 
   * @return Bean for DataSource definition with c3p0
   */
  @Bean(destroyMethod = "close")
  @ConfigurationProperties(prefix = "c3p0.datasource")
  @Profile(value = SpringProfileConstants.MYSQL)
  public ComboPooledDataSource dataSource() {

    return new ComboPooledDataSource();
  }

}
