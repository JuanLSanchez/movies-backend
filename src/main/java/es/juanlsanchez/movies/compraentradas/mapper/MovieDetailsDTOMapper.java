package es.juanlsanchez.movies.compraentradas.mapper;

import java.text.MessageFormat;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mapstruct.Mapper;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;

@Mapper(componentModel = "spring")
public interface MovieDetailsDTOMapper {


  default public MovieDetailsDTO fromDoc(String code, Document doc, String urlToGetMovie) {
    MovieDetailsDTO result = null;
    Elements elementDescription = doc.select("li[data-title]");
    String description = elementDescription.attr("data-description");

    if (!description.isEmpty()) {
      String srcImgLarge = doc.select(".rev-slidebg").attr("src");
      String srcImgPoster = doc.select("#slide-1-layer-2>img").attr("src");
      String hrefCinema = doc.select(".LinkCine").get(0).attr("href");
      String tit = hrefCinema.split(code)[1].substring(1);
      String href = MessageFormat.format(urlToGetMovie, code, tit);
      String title = elementDescription.attr("data-title");
      result = new MovieDetailsDTO(code, tit, href, title, description, srcImgPoster, srcImgLarge);
    }
    return result;
  }

}
