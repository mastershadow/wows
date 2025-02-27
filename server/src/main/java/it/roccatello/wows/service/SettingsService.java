package it.roccatello.wows.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import it.roccatello.wows.repository.SettingsRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SettingsService {
  @Autowired
  private ObjectMapper objectMapper;

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
    return this.data.getOrDefault(code.getCode(), null);
  }

  public Double getDouble(Code code) {
    return Double.parseDouble(this.get(code));
  }

  public Integer getInteger(Code code) {
    return Integer.parseInt(this.get(code));
  }

  public <T> List<T> getList(Code code) {
    try {
      return this.objectMapper.readValue(this.get(code), new TypeReference<List<T>>() {
      });
    } catch (final IOException e) {
      log.error("JSON reading error", e);
    }
    return null;
  }

  public enum Code {
    ENABLED_INTERVALS("enabled-intervals"),
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
