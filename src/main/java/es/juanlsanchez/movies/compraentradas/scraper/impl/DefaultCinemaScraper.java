package es.juanlsanchez.movies.compraentradas.scraper.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import es.juanlsanchez.movies.compraentradas.dto.CinemaDTO;
import es.juanlsanchez.movies.compraentradas.mapper.CinemaDTOMapper;
import es.juanlsanchez.movies.compraentradas.scraper.CinemaScraper;
import es.juanlsanchez.movies.compraentradas.service.JsoupCompraentradasService;
import es.juanlsanchez.movies.config.property.CompraentradaProperty;

@Component
public class DefaultCinemaScraper implements CinemaScraper {

  private final CompraentradaProperty compraentradaProperty;
  private final CinemaDTOMapper cinemaDTOMapper;
  private final JsoupCompraentradasService jsoupCompraentradasService;

  public DefaultCinemaScraper(final CompraentradaProperty compraentradaProperty,
      final CinemaDTOMapper cinemaDTOMapper,
      final JsoupCompraentradasService jsoupCompraentradasService) {
    this.compraentradaProperty = compraentradaProperty;
    this.cinemaDTOMapper = cinemaDTOMapper;
    this.jsoupCompraentradasService = jsoupCompraentradasService;
  }

  @Override
  public Optional<CinemaDTO> findOne(String code) throws IOException {
    String urlToGetCinema = compraentradaProperty.getUrlToGetCinema();
    String url = MessageFormat.format(urlToGetCinema, code, "a");
    Document doc = jsoupCompraentradasService.get(url);

    CinemaDTO result = this.cinemaDTOMapper.fromDocument(code, doc, urlToGetCinema);

    return Optional.ofNullable(result);
  }

}
