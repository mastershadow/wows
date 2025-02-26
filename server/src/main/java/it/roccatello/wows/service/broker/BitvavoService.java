package it.roccatello.wows.service.broker;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class BitvavoService extends BrokerService {
  
  @Override
  public String getCode() {
    return "BITVAVO";
  }
}
