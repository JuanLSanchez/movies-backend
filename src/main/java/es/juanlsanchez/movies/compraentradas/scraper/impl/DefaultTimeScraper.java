package es.juanlsanchez.movies.compraentradas.scraper.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import es.juanlsanchez.movies.compraentradas.dto.TimeDTO;
import es.juanlsanchez.movies.compraentradas.scraper.TimeScraper;
import es.juanlsanchez.movies.compraentradas.service.JsoupCompraentradasService;
import es.juanlsanchez.movies.config.property.CompraentradaProperty;

@Component
public class DefaultTimeScraper implements TimeScraper {

  static final DateTimeFormatter INSTANT_PARSER = new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd HH:mm").toFormatter().withZone(ZoneId.of("Europe/Paris"));

  private final CompraentradaProperty compraentradaProperty;
  private final JsoupCompraentradasService jsoupCompraentradasService;

  public DefaultTimeScraper(final CompraentradaProperty compraentradaProperty,
      final JsoupCompraentradasService jsoupCompraentradasService) {
    this.compraentradaProperty = compraentradaProperty;
    this.jsoupCompraentradasService = jsoupCompraentradasService;
  }

  @Override
  public List<TimeDTO> findOneByDayAndCinemaAndMovie(LocalDate day, String cinemaCode,
      String movieCode) throws IOException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String url = MessageFormat.format(compraentradaProperty.getUrlToGetTimes(),
        day.format(formatter), cinemaCode, movieCode);
    Document doc = jsoupCompraentradasService.get(url);
    List<TimeDTO> result = new ArrayList<TimeDTO>();

    for (Element element : doc.select("a.letrashorasDetalle")) {
      String href = element.attr("href");
      Instant instant = calculateInstant(element.text(), day);
      Double price = priceFromTimeUrl(href);
      TimeDTO time = new TimeDTO(href, instant, price, cinemaCode, movieCode);
      result.add(time);
    }

    return result;
  }

  private Instant calculateInstant(String text, LocalDate day) {
    String strInstant;
    if (text.substring(0, 2).equals("00")) {
      strInstant = day.plusDays(1L).toString() + " " + text;
    } else {
      strInstant = day.toString() + " " + text;
    }
    Instant result = INSTANT_PARSER.parse(strInstant, Instant::from);
    return result;
  }

  private Double priceFromTimeUrl(String href) throws IOException {
    Document theatersDoc = jsoupCompraentradasService.get(href);
    Element buyButton = theatersDoc.select("#CapaOcultaBoton a").get(0);
    String priceHref = buyButton.attr("href");
    Document priceDoc = jsoupCompraentradasService.get(priceHref);
    String strPrice = priceDoc.select("#LineaPrecios span").text().replaceAll(" €", "");
    Double result;
    try {
      result = Double.valueOf(strPrice);
    } catch (NumberFormatException e) {
      result = null;
    }
    return result;
  }


}
