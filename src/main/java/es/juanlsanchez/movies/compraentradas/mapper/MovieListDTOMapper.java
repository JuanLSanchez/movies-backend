package es.juanlsanchez.movies.compraentradas.mapper;

import org.jsoup.nodes.Element;

import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;

public interface MovieListDTOMapper {

  public MovieListDTO fromJsoupElement(Element option);

}
