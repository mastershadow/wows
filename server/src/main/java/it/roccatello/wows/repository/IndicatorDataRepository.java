package it.roccatello.wows.repository;

import it.roccatello.wows.model.db.Indicator;
import it.roccatello.wows.model.db.IndicatorData;
import it.roccatello.wows.model.db.Interval;
import it.roccatello.wows.model.db.Ticker;

import java.util.List;


public interface IndicatorDataRepository extends BaseRepository<IndicatorData> {
  List<IndicatorData> findByTickerAndIndicatorAndInterval(Ticker ticker, Indicator indicator, Interval interval);
  List<IndicatorData> findByTickerAndIndicatorAndIntervalAndStartLessThanEqual(Ticker ticker, Indicator indicator, Interval interval, long start);
}
