package es.juanlsanchez.movies.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "time_history")
public class TimeHistory extends BaseEntity {

  @Column(name = "instant")
  private Instant instant;
  @Column(name = "number_of_free_seat")
  private Integer numberOfFreeSeat;
  @Column(name = "number_of_total_seat")
  private Integer numberOfTotalSeat;
  @Column(name = "seats", columnDefinition = "text", length = 65535)
  private String seats;

  // Relationships
  @ManyToOne(optional = false)
  private Time time;
}
