package it.roccatello.wows.model.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoCandle extends BaseDtoModel implements Comparable<DtoCandle> {
  private Long id;
  private String interval;
  private String ticker;
  private Long occurred;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal min;
  private BigDecimal max;
  private BigDecimal volume;
  private Boolean generated = false;

  @Override
  public int compareTo(DtoCandle o) {
    val t = ticker.compareTo(o.getTicker());
    if (t != 0) {
      return t;
    }

    val i = interval.compareTo(o.getInterval());
    if (i != 0) {
      return i;
    }
    
    return this.getOccurred().compareTo(o.getOccurred());
  }
}
