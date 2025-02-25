package it.roccatello.wows.model.db;

import java.math.BigDecimal;
import java.util.Map;

import it.roccatello.wows.util.HashMapConverter;
import jakarta.persistence.Convert;
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
  @Convert(converter = HashMapConverter.class)
  private Map<String, Object> attrs;
}
