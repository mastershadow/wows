package it.roccatello.wows.model.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoCandle extends BaseDtoModel {
  private Long id;
  private String interval;
  private String ticker;
  private Long occurred;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal min;
  private BigDecimal max;
  private BigDecimal volume;
}
