package es.juanlsanchez.movies.compraentradas.mapper;

import org.jsoup.nodes.Document;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;

public interface MovieDetailsDTOMapper {

  public MovieDetailsDTO fromDoc(String code, Document doc, String urlToGetMovie);

}
