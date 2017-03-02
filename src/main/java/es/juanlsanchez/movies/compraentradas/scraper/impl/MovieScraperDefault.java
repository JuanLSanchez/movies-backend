package es.juanlsanchez.movies.compraentradas.scraper.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import es.juanlsanchez.movies.compraentradas.dto.MovieDetailsDTO;
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

    return movies.subList(1, movies.size()).stream()
        .map(element -> movieListDTOMapper.fromJsoupElement(element)).collect(Collectors.toList());
  }

  @Override
  public Optional<MovieDetailsDTO> findOne(String code) throws IOException {
    MovieDetailsDTO result = null;
    String url = MessageFormat.format("http://www.compraentradas.com/CinesPelicula/{0}/a", code);
    Document doc = Jsoup.connect(url).headers(JsoupConstants.HEADERS).get();
    Elements elementDescription = doc.select("li[data-title]");
    String description = elementDescription.attr("data-description");

    if (!description.isEmpty()) {
      String srcImgLarge = doc.select(".rev-slidebg").attr("src");
      String srcImgPoster = doc.select("#slide-1-layer-2>img").attr("src");
      String hrefCinema = doc.select(".LinkCine").get(0).attr("href");
      String tit = hrefCinema.split(code)[1].substring(1);
      String href = "http://www.compraentradas.com/CinesPelicula/" + code + "/" + tit;
      String title = elementDescription.attr("data-title");
      result = new MovieDetailsDTO(code, tit, href, title, description, srcImgPoster, srcImgLarge);
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<MovieDetailsDTO> findOneByMovieListDTO(MovieListDTO movieListDTO)
      throws IOException {

    return findOne(movieListDTO.getCode());
  }

}
