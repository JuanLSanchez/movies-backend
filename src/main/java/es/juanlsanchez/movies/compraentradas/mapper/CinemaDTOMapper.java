package es.juanlsanchez.movies.compraentradas.mapper;

import java.text.MessageFormat;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import es.juanlsanchez.movies.compraentradas.dto.CinemaDTO;
import es.juanlsanchez.movies.domain.Cinema;

@Mapper(componentModel = "spring")
public interface CinemaDTOMapper {

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "times", ignore = true)})
  public Cinema toDomainCinema(CinemaDTO cinemaDTO);

  default public CinemaDTO fromDocument(String code, Document doc, String urlToGetCinema) {
    CinemaDTO result = null;
    if (doc.head().childNodeSize() > 0 && doc.select(".peliseleccionable").size() > 0) {
      Elements spans = doc.select("#contenido3 div div span");

      String tit = doc.select(".peliseleccionable").get(0).attr("nombrecine");
      String href = MessageFormat.format(urlToGetCinema, code, tit);
      String name = doc.select("#slide-1-layer-2 > span").text().replace("Bienvenido a ", "");
      String address = spans.get(9).text();
      String city = spans.get(11).text();
      String province = spans.get(13).text();
      String phone = spans.get(17).text();
      String email = spans.get(19).text();
      Integer numberOfTheaters;

      try {
        numberOfTheaters = Integer.parseInt(spans.get(27).text());
      } catch (Exception e) {
        numberOfTheaters = null;
      }

      result = new CinemaDTO(code, tit, href, name, address, city, province, phone, email,
          numberOfTheaters);
    }
    return result;
  }

}
