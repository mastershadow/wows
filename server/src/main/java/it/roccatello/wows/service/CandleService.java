package it.roccatello.wows.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.data.BrokerCandleResponse;
import it.roccatello.wows.model.db.Candle;
import it.roccatello.wows.model.db.Interval;
import it.roccatello.wows.model.db.Ticker;
import it.roccatello.wows.model.dto.DtoCandle;
import it.roccatello.wows.repository.CandleRepository;
import it.roccatello.wows.service.IntervalService.IntervalConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CandleService {

  @Autowired
  private CandleRepository repository;

  @Lazy
  @Autowired
  private TickerService tickerService;

  @Lazy
  @Autowired
  private IntervalService intervalService;

  public void save(BrokerCandleResponse res) {
    var t = this.tickerService.ticker(res.getTicker());
    var i = this.intervalService.interval(IntervalConst.fromCode(res.getInterval()));
    var values = res.getValues();
    if (values == null) {
      return;
    }
    var candles = values.stream().sorted((c1, c2) -> c1.getOccurred().compareTo(c2.getOccurred())).toList();
    log.debug("Received {} candles for {}", candles.size(), res);
    var existingTimestamp = this.repository
        .findByTickerAndIntervalAndOccurredGreaterThanEqual(t, i, candles.get(0).getOccurred())
        .stream().map(Candle::getOccurred).collect(Collectors.toSet());
        
    var filteredCandles = candles.stream().filter(c -> !existingTimestamp.contains(c.getOccurred())).toList();
    log.debug("Adding non existing {} candle(s).", filteredCandles.size());
    filteredCandles.forEach(c -> {
      this.repository.save(this.convertToEntity(c, t, i));
    });
    this.repository.flush();
  }

  private Candle convertToEntity(DtoCandle d, Ticker t, Interval i) {
    var candle = new Candle();
    candle.setInterval(i);
    candle.setTicker(t);
    candle.setOccurred(d.getOccurred());
    candle.setClose(d.getClose());
    candle.setOpen(d.getOpen());
    candle.setMin(d.getMin());
    candle.setMax(d.getMax());
    candle.setVolume(d.getVolume());
    return candle;
  }

}
