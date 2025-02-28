package it.roccatello.wows.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Ticker;
import it.roccatello.wows.repository.TickerRepository;

@Service
public class TickerService extends EntityManagerAwareService {

  @Autowired
  private TickerRepository repository;

  public List<Ticker> enabledTickers() {
    return this.detach(this.repository.findAllByEnabledTrue());
  }

  public Ticker ticker(String ticker) {
    return this.repository.findOneByTicker(ticker);
  }
}
