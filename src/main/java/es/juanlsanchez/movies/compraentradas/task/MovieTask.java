package es.juanlsanchez.movies.compraentradas.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import es.juanlsanchez.movies.task.Task;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
@ConditionalOnProperty("app.properties.task.movie")
public class MovieTask implements Task {

  @Override
  @Scheduled(cron = "${app.properties.task.movie}")
  public void run() {
    log.debug("Run movie task");
  }

}
