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
public class Trade extends BaseDbModel {

  @ManyToOne
  private Ticker ticker;

  private BigDecimal qty;
  private BigDecimal buyPrice;
  private BigDecimal maxPrice;
  private BigDecimal minPrice;
  private BigDecimal sellPrice;
  private BigDecimal margin;

  private LocalDateTime buyTimestamp;
  private LocalDateTime sellTimestamp;
}
