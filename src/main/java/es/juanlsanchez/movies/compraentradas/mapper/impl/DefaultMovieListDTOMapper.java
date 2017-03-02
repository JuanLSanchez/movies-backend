package es.juanlsanchez.movies.compraentradas.mapper.impl;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;
import es.juanlsanchez.movies.compraentradas.mapper.MovieListDTOMapper;

@Component
public class DefaultMovieListDTOMapper implements MovieListDTOMapper {

  @Override
  public MovieListDTO fromJsoupElement(Element option) {
    String tit = option.attr("tit");
    String code = option.attr("value");
    String title = option.text();
    String href = "http://www.compraentradas.com/CinesPelicula/" + code + "/" + tit;
    return new MovieListDTO(code, tit, href, title);
  }

}
