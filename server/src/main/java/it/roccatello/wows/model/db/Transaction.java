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
public class Transaction extends BaseDbModel {

  @ManyToOne
  private Ticker ticker;
  @Column(precision = 32, scale = 10)
  private BigDecimal qty;
  @Column(precision = 32, scale = 10)
  private BigDecimal price;
  @Column(precision = 32, scale = 10)
  private BigDecimal total;
  @Column(precision = 32, scale = 10)
  private BigDecimal costs;

  private LocalDateTime occurred;
}
