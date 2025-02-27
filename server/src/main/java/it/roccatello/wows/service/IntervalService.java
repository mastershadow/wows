package it.roccatello.wows.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Interval;
import it.roccatello.wows.model.db.Provider;
import it.roccatello.wows.repository.IntervalRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Accessors(fluent = true)
public class IntervalService extends EntityManagerAwareService {

  @Autowired
  private IntervalRepository repository;

  @Getter
  private Map<IntervalConst, Interval> intervals;

  @PostConstruct
  private void postConstruct() {
    this.intervals = this.repository.findAll().stream().peek(this.entityManager::detach)
        .collect(Collectors.toMap(i -> IntervalConst.fromCode(i.getCode()), Function.identity()));
  }

  public Interval interval(IntervalConst ic) {
    return this.intervals.getOrDefault(ic, null);
  }

  public enum IntervalConst {
    I_1M("1m"),
    I_5M("5m"),
    I_15M("15m"),
    I_30M("30m"),
    I_1H("1h"),
    I_2H("2h"),
    I_4H("4h"),
    I_6H("6h"),
    I_8H("8h"),
    I_16H("16h"),
    I_1D("1d");

    @Getter
    private String code;

    IntervalConst(String code) {
      this.code = code;
    }

    public static IntervalConst fromCode(final String code) {
      return Arrays.stream(values()).filter(v -> v.code().equals(code)).findFirst().orElseThrow();
    }
  }

}
