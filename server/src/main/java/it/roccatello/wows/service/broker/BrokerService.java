package it.roccatello.wows.service.broker;

import org.springframework.beans.factory.annotation.Autowired;

import it.roccatello.wows.model.data.BrokerCandleRequest;
import it.roccatello.wows.model.data.BrokerCandleResponse;
import it.roccatello.wows.model.db.Provider;
import it.roccatello.wows.service.RestHttpService;

public abstract class BrokerService {
  @Autowired
  protected RestHttpService httpService;

  public abstract String code();
  public abstract void configure(Provider provider);
  public abstract boolean enabled();

  public abstract BrokerCandleResponse fetchCandles(BrokerCandleRequest request);
}
