package it.roccatello.wows.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Indicator;
import it.roccatello.wows.model.db.IndicatorData;
import it.roccatello.wows.model.db.Interval;
import it.roccatello.wows.model.db.Ticker;
import it.roccatello.wows.model.dto.DtoIndicatorData;
import it.roccatello.wows.repository.IndicatorDataRepository;
import it.roccatello.wows.repository.IndicatorRepository;

@Service
public class IndicatorService extends EntityManagerAwareService {

  @Autowired
  private IndicatorRepository repository;

  @Autowired
  private IndicatorDataRepository dataRepository;

  public List<Indicator> enabledIndicators() {
    return this.detach(this.repository.findByEnabledTrue());
  }

  public List<IndicatorData> listData(Indicator indicator, Ticker ticker, Interval interval, Long since) {
    if (since == null) {
      return this.detach(this.dataRepository.findByTickerAndIndicatorAndInterval(ticker, indicator, interval));  
    }
    return this.detach(this.dataRepository.findByTickerAndIndicatorAndIntervalAndStartLessThanEqual(ticker, indicator, interval, since));
  }

  public void saveData(DtoIndicatorData data) {

  }


}
