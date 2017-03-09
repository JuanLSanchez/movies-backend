package es.juanlsanchez.movies.compraentradas.scraper.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;
import es.juanlsanchez.movies.compraentradas.dto.MovieListDTO;
import es.juanlsanchez.movies.compraentradas.mapper.MovieDetailsDTOMapper;
import es.juanlsanchez.movies.compraentradas.mapper.MovieListDTOMapper;
import es.juanlsanchez.movies.compraentradas.scraper.MovieScraper;
import es.juanlsanchez.movies.config.constants.JsoupConstants;
import es.juanlsanchez.movies.config.property.CompraentradaProperty;

@Component
public class DefaultMovieScraper implements MovieScraper {

  private final CompraentradaProperty compraentradaProperty;
  private final MovieListDTOMapper movieListDTOMapper;
  private final MovieDetailsDTOMapper movieDetailsDTOMapper;

  public DefaultMovieScraper(final CompraentradaProperty compraentradaProperty,
      final MovieListDTOMapper movieListDTOMapper,
      final MovieDetailsDTOMapper movieDetailsDTOMapper) {
    this.compraentradaProperty = compraentradaProperty;
    this.movieListDTOMapper = movieListDTOMapper;
    this.movieDetailsDTOMapper = movieDetailsDTOMapper;
  }

  @Override
  public List<MovieListDTO> findAllActual() throws IOException {
    String url = compraentradaProperty.getUrlToListMovies();
    String cssQuery = compraentradaProperty.getCssQueryToListMovies();

    Document doc = Jsoup.connect(url).headers(JsoupConstants.HEADERS).get();
    Elements movies = doc.select(cssQuery);

    return movies.subList(1, movies.size()).stream()
        .map(element -> movieListDTOMapper.fromJsoupElement(element)).collect(Collectors.toList());
  }

  @Override
  public Optional<MovieDetailsDTO> findOne(String code) throws IOException {
    String urlToGetMovie = compraentradaProperty.getUrlToGetMovie();
    String url = MessageFormat.format(urlToGetMovie, code, "a");
    Document doc = Jsoup.connect(url).headers(JsoupConstants.HEADERS).get();

    MovieDetailsDTO result = this.movieDetailsDTOMapper.fromDoc(code, doc, urlToGetMovie);

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<MovieDetailsDTO> findOneByMovieListDTO(MovieListDTO movieListDTO)
      throws IOException {

    return findOne(movieListDTO.getCode());
  }

}
