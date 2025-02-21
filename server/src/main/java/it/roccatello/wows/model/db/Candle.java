package it.roccatello.wows.model.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Candle extends BaseDbModel {
  @ManyToOne
  private Ticker ticker;
  @ManyToOne
  private Interval interval;
  private LocalDateTime occurred;

  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal min;
  private BigDecimal max;

}
