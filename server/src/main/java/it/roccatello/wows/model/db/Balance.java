package it.roccatello.wows.model.db;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Balance extends BaseDbModel {
  @Column(precision = 32, scale = 10)
  private BigDecimal available;
}
