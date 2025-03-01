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
public class Trade extends BaseDbModel {

  @ManyToOne
  private Ticker ticker;
  @Column(precision = 32, scale = 10)
  private BigDecimal qty;
  @Column(precision = 32, scale = 10)
  private BigDecimal buyPrice;
  @Column(precision = 32, scale = 10)
  private BigDecimal maxPrice;
  @Column(precision = 32, scale = 10)
  private BigDecimal minPrice;
  @Column(precision = 32, scale = 10)
  private BigDecimal sellPrice;
  @Column(precision = 32, scale = 10)
  private BigDecimal margin;

  private Boolean open = Boolean.TRUE;
  private Boolean stopLoss = Boolean.TRUE;
  private Boolean manual = Boolean.FALSE;

  private LocalDateTime buyTimestamp;
  private LocalDateTime sellTimestamp;
}
