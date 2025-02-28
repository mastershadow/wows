package it.roccatello.wows.service.broker;

import java.time.Instant;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.data.BrokerCandleRequest;
import it.roccatello.wows.model.data.BrokerCandleResponse;
import it.roccatello.wows.model.db.Provider;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

@Lazy
@Service
@Accessors(fluent = true)
public class BitvavoService extends BrokerService {
  private static final String HDR_ACCESS_KEY = "Bitvavo-Access-Key";
  private static final String HDR_ACCESS_TIMESTAMP = "Bitvavo-Access-Timestamp";
  private static final String HDR_ACCESS_SIGNATURE = "Bitvavo-Access-Signature";

  @Getter
  @Setter
  private String apiKey;

  @Getter
  @Setter
  private String apiSecret;

  @Getter(onMethod = @__({ @Override }))
  @Setter
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

  protected Request buildRequest(String url, boolean isPost, String body) {
    var timestamp = Instant.now().toEpochMilli();
    var b = new Request.Builder()
        .url(url)
        .addHeader(HDR_ACCESS_KEY, this.apiKey())
        .addHeader(HDR_ACCESS_TIMESTAMP, String.valueOf(timestamp))
        .addHeader(HDR_ACCESS_SIGNATURE, this.signature(url, isPost ? "POST" : "GET", body, timestamp));

    if (isPost) {
      b.post(RequestBody.create(body, MediaType.get("application/json")));
    }

    return b.build();
  }

  protected String signature(String url, String method, String body, long timestamp) {
    return null;
  }
}
