package it.roccatello.wows.model.dto;


import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoIndicatorData extends BaseDtoModel {
  private String indicator;
  private String interval;
  private String ticker;
  private List<BigDecimal> data;
}
