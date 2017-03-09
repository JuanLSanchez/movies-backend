package es.juanlsanchez.movies.compraentradas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO {

  private String code;
  private String tit;
  private String href;

  // Data
  private String name;
  private String address;
  private String city;
  private String province;
  private String phone;
  private String email;
  private Integer numberOfTheaters;

}
