package it.roccatello.wows.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.repository.BalanceRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BalanceService {

  @Autowired
  private BalanceRepository repository;

  public BigDecimal getAvailable() {
    return this.repository.findAll().getFirst().getAvailable();
  }

  public void setAvailable(BigDecimal avail) {
    var balance = this.repository.findAll().getFirst();
    balance.setAvailable(avail);
    this.repository.saveAndFlush(balance);
  }

}
