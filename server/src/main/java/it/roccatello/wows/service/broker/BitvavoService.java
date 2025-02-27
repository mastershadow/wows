package it.roccatello.wows.service.broker;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Provider;
import lombok.Getter;
import lombok.Setter;

@Lazy
@Service
public class BitvavoService extends BrokerService {
  @Getter @Setter
  private String apiKey;

  @Getter @Setter
  private String apiSecret;
  
  @Override
  public String getCode() {
    return "BITVAVO";
  }

  @Override
  public void configure(Provider provider) {
    this.apiKey = String.valueOf(provider.getAttrs().get("key"));
    this.apiSecret = String.valueOf(provider.getAttrs().get("secret"));
  }
}
