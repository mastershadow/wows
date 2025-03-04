package it.roccatello.wows.model.db;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Candle extends BaseDbModel {
  @ManyToOne(fetch = FetchType.LAZY)
  private Ticker ticker;
  @ManyToOne(fetch = FetchType.LAZY)
  private Interval interval;
  private Long occurred;

  @Column(precision = 32, scale = 10)
  private BigDecimal open;
  @Column(precision = 32, scale = 10)
  private BigDecimal close;
  @Column(precision = 32, scale = 10)
  private BigDecimal min;
  @Column(precision = 32, scale = 10)
  private BigDecimal max;
  @Column(precision = 32, scale = 10)
  private BigDecimal volume;

}
