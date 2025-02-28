package it.roccatello.wows.service.broker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.roccatello.wows.model.data.BrokerCandleRequest;
import it.roccatello.wows.model.data.BrokerCandleResponse;
import it.roccatello.wows.model.db.Provider;
import it.roccatello.wows.model.dto.DtoCandle;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

@Lazy
@Slf4j
@Service
@Accessors(fluent = true)
public class BitvavoService extends BrokerService {
  private static final String HDR_ACCESS_KEY = "Bitvavo-Access-Key";
  private static final String HDR_ACCESS_TIMESTAMP = "Bitvavo-Access-Timestamp";
  private static final String HDR_ACCESS_SIGNATURE = "Bitvavo-Access-Signature";
  private static final String BASE_URL = "https://api.bitvavo.com/v2";
  private static final String API_CANDLES_URL = "/{0}/candles?interval={1}";


  @Getter
  @Setter
  private String apiKey;

  @Getter
  @Setter
  private String apiSecret;

  @Getter(onMethod = @__({ @Override }))
  @Setter
  private boolean enabled;

  @Autowired
  private ObjectMapper objectMapper;

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
    var url = MessageFormat.format(API_CANDLES_URL, request.getTicker(), request.getInterval());
    val res = this.httpService.request(this.buildRequest(url, false, null));
    if (res != null) {
      try {
				var candles = this.<List<List<Object>>>convertString(res.body().string());
        if (candles != null) {
          var cdtos =candles.stream().map(ent -> {
            var dto = new DtoCandle();
            dto.setOccurred((Long)ent.get(0));
            dto.setOpen(NumberUtils.toScaledBigDecimal((String)ent.get(1), 10, RoundingMode.HALF_UP));
            dto.setMax(NumberUtils.toScaledBigDecimal((String)ent.get(2), 10, RoundingMode.HALF_UP));
            dto.setMin(NumberUtils.toScaledBigDecimal((String)ent.get(3), 10, RoundingMode.HALF_UP));
            dto.setClose(NumberUtils.toScaledBigDecimal((String)ent.get(4), 10, RoundingMode.HALF_UP));
            dto.setVolume(NumberUtils.toScaledBigDecimal((String)ent.get(5), 10, RoundingMode.HALF_UP));
            
            return dto;
          }).toList();
          return new BrokerCandleResponse(request.getTicker(), request.getInterval(), cdtos);
        }
			} catch (IOException ex) {
        log.error("{}", ex);
			}
    }
    return null;
  }

  protected <T> T convertString(String string) {
    try {
			return this.objectMapper.readValue(string, new TypeReference<T>() {
			});
		} catch (JsonProcessingException ex) {
      log.error("{}", ex);
		}
    return null;
  }

  protected Request buildRequest(String url, boolean isPost, String body) {
    var timestamp = Instant.now().toEpochMilli();
    var b = new Request.Builder()
        .url(BASE_URL + url)
        .addHeader(HDR_ACCESS_KEY, this.apiKey())
        .addHeader(HDR_ACCESS_TIMESTAMP, String.valueOf(timestamp))
        .addHeader(HDR_ACCESS_SIGNATURE, this.signature("/v2" + url, isPost ? "POST" : "GET", body, timestamp));

    if (isPost) {
      b.post(RequestBody.create(body, MediaType.get("application/json")));
    }

    return b.build();
  }

  protected String signature(String url, String method, String body, long timestamp) {
    var result = String.valueOf(timestamp) + method + url;
    if (body != null) {
      result += body;
    }
    try {
      var hmac = Mac.getInstance("HmacSHA256");
      var secretKey = new SecretKeySpec(this.apiSecret.getBytes("UTF-8"), "HmacSHA256");
      hmac.init(secretKey);
      return new String(Hex.encodeHex(hmac.doFinal(result.getBytes("UTF-8"))));
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException ex) {
      log.error("{}", ex);
    }
    return null;
  }

}
