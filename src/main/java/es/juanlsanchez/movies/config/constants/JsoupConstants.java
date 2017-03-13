package es.juanlsanchez.movies.config.constants;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class JsoupConstants {

  public static final Map<String, String> HEADERS = Maps.newHashMap(ImmutableMap.of("Cookie",
      "__HFUID={0};__utmt=1", "Accept-Language", "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3"));

}
