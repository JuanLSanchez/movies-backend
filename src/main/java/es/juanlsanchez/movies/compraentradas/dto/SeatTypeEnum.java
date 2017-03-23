package es.juanlsanchez.movies.compraentradas.dto;

import lombok.Getter;

@Getter
public enum SeatTypeEnum {
  free("http://www.compraentradas.com/images/asientos/libre16.png"), //
  for_chair("http://www.compraentradas.com/images/asientos/parasilla.png"), //
  occupied("http://www.compraentradas.com/images/asientos/ocupado16.png"), //
  selected("http://www.compraentradas.com/images/asientos/seleccionado16.png"), //
  special("http://www.compraentradas.com/images/asientos/especial.png"), //
  reserved("http://www.compraentradas.com/images/asientos/reservado.png"); //

  private String url;

  SeatTypeEnum(String url) {
    this.url = url;
  }

  public String toJavaScriptString() {
    return "'" + this.toString() + "'";
  }
}
