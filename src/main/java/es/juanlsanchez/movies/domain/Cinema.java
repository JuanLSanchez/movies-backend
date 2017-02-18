package es.juanlsanchez.movies.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@Table(name = "cinema")
public class Cinema extends BaseEntity {

  @Column(name = "code", length = 100)
  private String code;
  @Column(name = "tit", length = 100)
  private String tit;
  @Column(name = "href", length = 255)
  private String href;

  // Data
  @Column(name = "name", length = 100)
  private String name;
  @Column(name = "address", length = 255)
  private String address;
  @Column(name = "city", length = 100)
  private String city;
  @Column(name = "province", length = 100)
  private String province;
  @Column(name = "phone", length = 20)
  private String phone;
  @Column(name = "email", length = 100)
  private String email;
  @Column(name = "number_of_theaters")
  private Integer numberOfTheaters;

  // Relationships
  @OneToMany(mappedBy = "cinema")
  private List<Time> times;

}
