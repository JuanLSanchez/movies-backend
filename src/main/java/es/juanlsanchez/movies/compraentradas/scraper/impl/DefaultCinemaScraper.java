package es.juanlsanchez.movies.compraentradas.scraper.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import es.juanlsanchez.movies.compraentradas.dto.CinemaDTO;
import es.juanlsanchez.movies.compraentradas.mapper.CinemaDTOMapper;
import es.juanlsanchez.movies.compraentradas.scraper.CinemaScraper;
import es.juanlsanchez.movies.config.constants.JsoupConstants;
import es.juanlsanchez.movies.config.property.CompraentradaProperty;

@Component
public class DefaultCinemaScraper implements CinemaScraper {

  private final CompraentradaProperty compraentradaProperty;
  private final CinemaDTOMapper cinemaDTOMapper;

  public DefaultCinemaScraper(final CompraentradaProperty compraentradaProperty,
      final CinemaDTOMapper cinemaDTOMapper) {
    this.compraentradaProperty = compraentradaProperty;
    this.cinemaDTOMapper = cinemaDTOMapper;
  }

  @Override
  public Optional<CinemaDTO> findOne(String code) throws IOException {
    String urlToGetCinema = compraentradaProperty.getUrlToGetCinema();
    String url = MessageFormat.format(urlToGetCinema, code, "a");
    Document doc = Jsoup.connect(url).headers(JsoupConstants.HEADERS).get();

    CinemaDTO result = this.cinemaDTOMapper.fromDocument(code, doc, urlToGetCinema);

    return Optional.ofNullable(result);
  }

}
