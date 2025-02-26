package it.roccatello.wows.service.broker;

import org.springframework.beans.factory.annotation.Autowired;

import it.roccatello.wows.service.RestHttpService;

public abstract class BrokerService {
  @Autowired
  protected RestHttpService httpService;

  public abstract String getCode();

}
