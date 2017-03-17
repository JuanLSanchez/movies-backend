package es.juanlsanchez.movies.compraentradas.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeDTO {

  private String href;
  private Instant instant;
  private Double price;

  private String cinemaCode;
  private String movieCode;

}
