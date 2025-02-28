package it.roccatello.wows.service.broker;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.data.BrokerCandleRequest;
import it.roccatello.wows.model.data.BrokerCandleResponse;
import it.roccatello.wows.model.db.Provider;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Lazy
@Service
@Accessors(fluent = true)
public class BitvavoService extends BrokerService {
  @Getter @Setter
  private String apiKey;

  @Getter @Setter
  private String apiSecret;

  @Getter(onMethod = @__({@Override})) @Setter
  private boolean enabled;
  
  @Override
  public String code() {
    return "BITVAVO";
  }

  @Override
  public void configure(Provider provider) {
    this.apiKey = String.valueOf(provider.getAttrs().get("key"));
    this.apiSecret = String.valueOf(provider.getAttrs().get("secret"));
    this.enabled = BooleanUtils.isTrue(provider.getEnabled());
  }

  @Override
  public BrokerCandleResponse fetchCandles(BrokerCandleRequest request) {
    return null;
  }
}
