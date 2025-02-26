package it.roccatello.wows.model.db;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticker extends BaseDbModel {
  private String ticker;
  private Boolean enabled;
  private Boolean autotrade;
  @Column(nullable = true, precision = 32, scale = 10)
  private BigDecimal keepMinimum;
}
