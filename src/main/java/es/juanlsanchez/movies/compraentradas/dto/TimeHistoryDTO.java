package es.juanlsanchez.movies.compraentradas.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeHistoryDTO {

  private Instant instant;
  private Integer numberOfFreeSeat;
  private Integer numberOfTotalSeat;
  private String seats;

}
