package it.roccatello.wows.processing;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

import it.roccatello.wows.model.dto.DtoCandle;

public class BarSeriesConverter {

  public static BarSeries convert(List<DtoCandle> candles) {
    var series = new BaseBarSeriesBuilder().withName(null).build();
    candles.forEach(c -> {
      var endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(c.getOccurred()), ZoneOffset.UTC);
      series.addBar(endTime, c.getOpen(), c.getMax(), c.getMin(), c.getClose(), c.getVolume());
    });
    return series;
  }
   
}
