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
public class Transaction extends BaseDbModel {

  @ManyToOne
  private Ticker ticker;

  private BigDecimal qty;
  private BigDecimal price;
  private BigDecimal total;
  private BigDecimal costs;

  private LocalDateTime occurred;
}
