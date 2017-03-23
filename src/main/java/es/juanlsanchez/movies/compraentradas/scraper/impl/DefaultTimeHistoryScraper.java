package es.juanlsanchez.movies.compraentradas.scraper.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import es.juanlsanchez.movies.compraentradas.dto.SeatTypeEnum;
import es.juanlsanchez.movies.compraentradas.dto.TimeDTO;
import es.juanlsanchez.movies.compraentradas.dto.TimeHistoryDTO;
import es.juanlsanchez.movies.compraentradas.scraper.TimeHistoryScraper;
import es.juanlsanchez.movies.compraentradas.service.JsoupCompraentradasService;

@Component
public class DefaultTimeHistoryScraper implements TimeHistoryScraper {

  private final JsoupCompraentradasService jsoupCompraentradasService;

  public DefaultTimeHistoryScraper(final JsoupCompraentradasService jsoupCompraentradasService) {
    this.jsoupCompraentradasService = jsoupCompraentradasService;
  }

  @Override
  public TimeHistoryDTO findOneByTimeDTO(TimeDTO timeDTO) throws IOException {

    Document doc = jsoupCompraentradasService.get(timeDTO.getHref());
    List<List<SeatTypeEnum>> seatsList = new ArrayList<List<SeatTypeEnum>>();
    String selectMessage = "#PatioButacas img[src=''{0}'']";
    Instant instant = Instant.now();
    Integer numberOfFreeSeat = 0;
    Integer numberOfTotalSeat = 0;

    for (SeatTypeEnum seatType : SeatTypeEnum.values()) {
      Elements seats = doc.select(MessageFormat.format(selectMessage, seatType.getUrl()));
      int size = seats.size();
      numberOfTotalSeat += size;
      if (seatType.equals(SeatTypeEnum.for_chair) || seatType.equals(SeatTypeEnum.free)) {
        numberOfFreeSeat += size;
      }
      for (Element element : seats) {
        String onClick = element.parent().attr("onclick");
        Pattern p = Pattern.compile("(\\d{1,3}), (\\d{1,3})\\)");
        Matcher matcher = p.matcher(onClick);
        if (matcher.find()) {
          Integer row = Integer.valueOf(matcher.group(1));
          Integer column = Integer.valueOf(matcher.group(2));
          addSeatToList(seatsList, seatType, row, column);
        }
      }
    }

    String seats =
        seatsList.stream()
            .map(row -> row == null ? "null"
                : new String(row.stream()
                    .map(column -> column == null ? "null" : column.toJavaScriptString())
                    .collect(Collectors.joining(",", "[", "]"))))
            .collect(Collectors.joining(",", "[", "]"));
    TimeHistoryDTO result = new TimeHistoryDTO(instant, numberOfFreeSeat, numberOfTotalSeat, seats);
    return result;
  }

  private void addSeatToList(List<List<SeatTypeEnum>> seatsList, SeatTypeEnum seatType, Integer row,
      Integer column) {
    if (!(seatsList.size() > row)) {
      fillListWitNull(seatsList, row);
    }
    if (seatsList.get(row) == null) {
      seatsList.set(row, new ArrayList<SeatTypeEnum>());
    }
    List<SeatTypeEnum> seatRow = seatsList.get(row);
    if (!(seatRow.size() > column)) {
      fillListWitNull(seatRow, column);
    }
    seatRow.set(column, seatType);

  }

  private <T> void fillListWitNull(List<T> list, Integer index) {
    for (int i = list.size(); i <= index; i++) {
      list.add(null);
    }
  }

}
