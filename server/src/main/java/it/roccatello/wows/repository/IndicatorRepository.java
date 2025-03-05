package it.roccatello.wows.repository;

import java.util.List;

import it.roccatello.wows.model.db.Indicator;

public interface IndicatorRepository extends BaseRepository<Indicator> {
  List<Indicator> findByEnabledTrue();
}
