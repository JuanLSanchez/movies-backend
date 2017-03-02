package es.juanlsanchez.movies.compraentradas.scraper.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;
import es.juanlsanchez.movies.compraentradas.mapper.MovieListDTOMapper;
import es.juanlsanchez.movies.compraentradas.scraper.MovieScraper;
import es.juanlsanchez.movies.config.constants.JsoupConstants;
import es.juanlsanchez.movies.config.property.CompraentradaProperty;

@Service
public class MovieScraperDefault implements MovieScraper {

  private final CompraentradaProperty compraentradaProperty;
  private final MovieListDTOMapper movieListDTOMapper;

  public MovieScraperDefault(final CompraentradaProperty compraentradaProperty,
      final MovieListDTOMapper movieListDTOMapper) {
    this.compraentradaProperty = compraentradaProperty;
    this.movieListDTOMapper = movieListDTOMapper;
  }

  @Override
  public List<MovieListDTO> findAllActual() throws IOException {
    String url = compraentradaProperty.getUrlToListMovies();
    String cssQuery = compraentradaProperty.getCssQueryToListMovies();

    Document doc = Jsoup.connect(url).headers(JsoupConstants.HEADERS).get();
    Elements movies = doc.select(cssQuery);

    return movies.stream().map(element -> movieListDTOMapper.fromJsoupElement(element))
        .collect(Collectors.toList());
  }

}
