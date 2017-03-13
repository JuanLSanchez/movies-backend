package es.juanlsanchez.movies.compraentradas.service;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface JsoupCompraentradasService {

  public Document get(String url) throws IOException;

}
