package it.roccatello.wows.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import it.roccatello.wows.repository.SettingsRepository;
import jakarta.annotation.PostConstruct;

@Service
public class SettingsService {

  @Autowired
  private SettingsRepository repository;

  private Map<String, String> data = Maps.newHashMap();

  @PostConstruct
  private void postConstruct() {
    this.repository.findAll().forEach(s -> {
      this.data.put(s.getCode(), s.getValue());
    });
  }

  public String get(Code code) {
    return data.getOrDefault(code.getCode(), null);
  }

  public Double getDouble(Code code) {
    return Double.parseDouble(get(code));
  }

  public Integer getInteger(Code code) {
    return Integer.parseInt(get(code));
  }

  public enum Code {
    KEEP_BALANCE_AVAILABLE_RATIO("keep-balance-available-ratio"),
    MAX_BALANCE_ALLOCATION_RATIO("max-balance-allocation-per-ticker-ratio"),
    MIN_GAIN_MARGIN("min-gain-margin"),
    STOP_LOSS_WARNING("stop-loss-warning"),
    STOP_LOSS("stop-loss");

    private String code;

    private Code(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }
}
