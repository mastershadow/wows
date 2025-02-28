package it.roccatello.wows.model.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoBalance extends BaseDtoModel {
  private BigDecimal available;
}
