package it.roccatello.wows.model.db;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Asset extends BaseDbModel {
  @Column(precision = 32, scale = 10)
  private BigDecimal amount;

  @ManyToOne
  private Ticker ticker;
}
