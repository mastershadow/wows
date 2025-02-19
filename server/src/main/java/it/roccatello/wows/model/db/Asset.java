package it.roccatello.wows.model.db;


import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Asset extends BaseDbModel {
  private BigDecimal amount;

  @ManyToOne
  private Ticker ticker;
}
