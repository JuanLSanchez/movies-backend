package es.juanlsanchez.movies.domain;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "time")
@ToString(exclude = {"cinema", "movie", "timeHistories"})
public class Time extends BaseEntity {

  @Column(name = "href", length = 255)
  private String href;

  // Data
  @Column(name = "instant")
  private Instant instant;
  @Column(name = "price")
  private Double price;

  // Relationships
  @ManyToOne(optional = false)
  private Cinema cinema;
  @ManyToOne(optional = false)
  private Movie movie;
  @OneToMany(mappedBy = "time")
  private List<TimeHistory> timeHistories;


}
