package it.roccatello.wows.model.db;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IndicatorData extends BaseDbModel {
  @ManyToOne
  private Indicator indicator;
  
  @ManyToOne
  private Ticker ticker;

  @ManyToOne
  private Interval interval;

  private Long start;

  private Long end;

  @Column(precision = 32, scale = 10)
  private List<BigDecimal> data;
}
