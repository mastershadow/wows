package it.roccatello.wows.model.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoAsset extends BaseDtoModel {
  private Long id;
  private String ticker;
  private BigDecimal amount;
  private Long providerId;
  private String providerCode;
}
