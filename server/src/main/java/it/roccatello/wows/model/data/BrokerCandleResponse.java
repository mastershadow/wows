package it.roccatello.wows.model.data;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class BrokerCandleResponse {
  private String ticker;
  private String interval;
  private List<List<BigDecimal>> values;
}
