package it.roccatello.wows.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class BrokerCandleRequest {
  private String ticker;
  private String interval;
}
