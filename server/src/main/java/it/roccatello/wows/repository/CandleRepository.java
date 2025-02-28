package it.roccatello.wows.repository;

import java.util.List;

import it.roccatello.wows.model.db.Candle;
import it.roccatello.wows.model.db.Interval;
import it.roccatello.wows.model.db.Ticker;

public interface CandleRepository extends BaseRepository<Candle> {
  List<Candle> findByTickerAndIntervalAndOccurredGreaterThanEqual(Ticker ticker, Interval interval, Long occured);
}
