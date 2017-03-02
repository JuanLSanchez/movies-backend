package es.juanlsanchez.movies.compraentradas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieListDTO {

  private String code;
  private String tit;
  private String href;
  private String title;

}
