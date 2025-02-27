package it.roccatello.wows.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrokerCandleRequest {
  private String ticker;
  private String interval;
}
