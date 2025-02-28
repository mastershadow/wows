package it.roccatello.wows.model.data;

import java.util.List;

import it.roccatello.wows.model.dto.DtoCandle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class BrokerCandleResponse {
  private String ticker;
  private String interval;
  @ToString.Exclude
  private List<DtoCandle> values;
}
