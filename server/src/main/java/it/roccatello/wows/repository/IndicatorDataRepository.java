package it.roccatello.wows.repository;

import it.roccatello.wows.model.db.Indicator;
import it.roccatello.wows.model.db.IndicatorData;
import it.roccatello.wows.model.db.Ticker;

import java.util.List;


public interface IndicatorDataRepository extends BaseRepository<IndicatorData> {
  List<IndicatorData> findByTickerAndIndicator(Ticker ticker, Indicator indicator);
  List<IndicatorData> findByTickerAndIndicatorAndStartLessThanEqual(Ticker ticker, Indicator indicator, long start);
}
