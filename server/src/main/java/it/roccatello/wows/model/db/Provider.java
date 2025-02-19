package it.roccatello.wows.model.db;


import java.math.BigDecimal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Provider extends BaseDbModel {
  private String code;
  private Boolean enabled;
  private BigDecimal buySpread;
  private BigDecimal sellSpread;
  private BigDecimal fixedCommission;
}
