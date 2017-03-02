package es.juanlsanchez.movies.compraentradas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailsDTO {

  private String code;
  private String tit;
  private String href;

  // Data
  private String title;
  private String description;
  private String srcImgPoster;
  private String srcImgLarge;

}
