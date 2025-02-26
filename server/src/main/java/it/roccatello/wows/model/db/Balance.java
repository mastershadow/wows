package it.roccatello.wows.model.db;


import java.math.BigDecimal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Balance extends BaseDbModel {
  private BigDecimal available;
}
