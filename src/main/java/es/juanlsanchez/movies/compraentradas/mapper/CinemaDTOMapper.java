package es.juanlsanchez.movies.compraentradas.mapper;

import org.jsoup.nodes.Document;

import es.juanlsanchez.movies.compraentradas.dto.CinemaDTO;

public interface CinemaDTOMapper {

  public CinemaDTO fromDocument(String code, Document doc, String urlToGetCinema);

}
