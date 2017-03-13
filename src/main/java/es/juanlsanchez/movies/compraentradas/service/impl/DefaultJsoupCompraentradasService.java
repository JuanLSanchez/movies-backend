package es.juanlsanchez.movies.compraentradas.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import es.juanlsanchez.movies.compraentradas.service.JsoupCompraentradasService;
import es.juanlsanchez.movies.config.constants.JsoupConstants;
import es.juanlsanchez.movies.config.property.CompraentradaProperty;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultJsoupCompraentradasService implements JsoupCompraentradasService {

  private String hfuid;
  private String urlBase;

  public DefaultJsoupCompraentradasService(final CompraentradaProperty compraentradaProperty) {
    urlBase = compraentradaProperty.getUrlBase();
    if (hfuid == null || hfuid.isEmpty()) {
      loadHfuid();
    }
  }

  @Override
  public Document get(String url) throws IOException {
    Document result;
    try {
      result = getConnectionWithHeaders(url).get();
    } catch (HttpStatusException e) {
      log.warn("Error to make petition for '{}'", url);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e1) {
      }
      result = get(url);
    }
    return result;
  }

  private void loadHfuid() {
    try {
      Document doc = getConnectionWithHeaders(urlBase).ignoreHttpErrors(true).get();
      Elements scripts = doc.head().select("script");
      String script = scripts.get(scripts.size() - 1).toString();
      String[] hexStrings = getHexStrings(script);
      hfuid = decrypt(hexStrings);
      log.debug("Loaded Hfuid for compraentradas.com");
    } catch (IOException e) {
      log.warn("Fail to load hfuid for compraentradas.com", e);
    }
  }

  private String[] getHexStrings(String script) {
    Pattern p = Pattern.compile("toNumbers\\(\"([\\d|a|b|c|d|e|f]+)\"");
    Matcher matcher = p.matcher(script);
    String[] result = new String[3];
    int i = 0;
    while (matcher.find() && i < 3) {
      result[i] = matcher.group(1);
      i++;
    }
    return result;
  }


  private Connection getConnectionWithHeaders(String url) {
    Map<String, String> headers = Maps.newHashMap(JsoupConstants.HEADERS);
    String cookie = MessageFormat.format(headers.get("Cookie"), hfuid);
    headers.put("Cookie", cookie);
    return Jsoup.connect(url).headers(headers);
  }

  private String decrypt(String[] array) {
    try {
      return decrypt(array[0], array[1], array[2]);
    } catch (Exception e) {
      log.warn("Error to calculate aes for : {}.", array, e);
      return null;
    }
  }

  private String decrypt(String sKey, String sIv, String sEncrypted) throws Exception {
    final String alg = "AES";
    final String cI = "AES/CBC/NoPadding";
    byte[] key = hexStringToByteArray(sKey);
    byte[] iv = hexStringToByteArray(sIv);
    byte[] encrypted = hexStringToByteArray(sEncrypted);
    Cipher cipher = Cipher.getInstance(cI);
    SecretKeySpec skeySpec = new SecretKeySpec(key, alg);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
    byte[] enc = encrypted;
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
    byte[] decrypted = cipher.doFinal(enc);
    return DatatypeConverter.printHexBinary(decrypted).toLowerCase();
  }

  private static byte[] hexStringToByteArray(String s) {
    return DatatypeConverter.parseHexBinary(s);
  }

}
