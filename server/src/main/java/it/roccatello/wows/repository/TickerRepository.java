package it.roccatello.wows.repository;

import java.util.List;

import it.roccatello.wows.model.db.Ticker;

public interface TickerRepository extends BaseRepository<Ticker> {
  List<Ticker> findAllByEnabledTrue();
  Ticker findOneByTicker(String ticker);
}
