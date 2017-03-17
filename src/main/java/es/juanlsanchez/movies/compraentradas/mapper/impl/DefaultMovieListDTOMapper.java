package es.juanlsanchez.movies.compraentradas.mapper.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  @Override
  public MovieListDTO fromJsoupCinemaElement(Element element) {
    String nombreCine = element.attr("nombrecine");
    String code;
    Pattern p = Pattern.compile(nombreCine + "/(\\d+)/");
    Matcher matcher = p.matcher(element.select("div").attr("data-description"));
    if (matcher.find()) {
      code = matcher.group(1);
    } else {
      code = null;
    }
    String tit = element.attr("titulo");
    String href = "http://www.compraentradas.com/CinesPelicula/" + code + "/" + tit;
    String html = element.select("div").attr("data-description");
    String title = html.split("\\n")[0].split("<h3>")[1].split("br")[0].split("   +")[0];
    return new MovieListDTO(code, tit, href, title);
  }

}
