package it.roccatello.wows.model.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
