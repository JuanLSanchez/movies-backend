package es.juanlsanchez.movies.compraentradas.mapper;

import java.text.MessageFormat;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.util.StringUtils;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;
import es.juanlsanchez.movies.domain.Movie;

@Mapper(componentModel = "spring")
public interface MovieDetailsDTOMapper {

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "times", ignore = true)})
  public Movie toDomainMovie(MovieDetailsDTO movieDetailsDTO);

  default public MovieDetailsDTO fromDoc(String code, Document doc, String urlToGetMovie) {
    MovieDetailsDTO result = null;
    Elements elementDescription = doc.select("li[data-title]");
    String description = elementDescription.attr("data-description");
    String title = elementDescription.attr("data-title");

    if (!StringUtils.isEmpty(title)) {
      String srcImgLarge = doc.select(".rev-slidebg").attr("src");
      String srcImgPoster = doc.select("#slide-1-layer-2>img").attr("src");

      String tit = title.toLowerCase().replaceAll(" ", "-") + "?";
      if (doc.select(".LinkCine").size() > 0) {
        String hrefCinema = doc.select(".LinkCine").get(0).attr("href");
        tit = hrefCinema.split(code)[1].substring(1);
      }
      String href = MessageFormat.format(urlToGetMovie, code, tit);
      result = new MovieDetailsDTO(code, tit, href, title, description, srcImgPoster, srcImgLarge);
    }
    return result;
  }

}
