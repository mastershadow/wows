package it.roccatello.wows.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Interval;
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

  @Autowired
  private SettingsService settingsService;

  @Getter
  private Map<IntervalConst, Interval> intervals;
  
  @Getter
  private List<Interval> enabledIntervals;

  @PostConstruct
  private void postConstruct() {
    this.intervals = this.repository.findAll().stream().peek(this.entityManager::detach)
        .collect(Collectors.toMap(i -> IntervalConst.fromCode(i.getCode()), Function.identity()));

    this.enabledIntervals = this.repository
        .findAllById(this.settingsService.getList(SettingsService.Code.ENABLED_INTERVALS)).stream()
        .peek(this.entityManager::detach).toList();
  }

  public Interval interval(IntervalConst ic) {
    return this.intervals.getOrDefault(ic, null);
  }

  public enum IntervalConst {
    I_1M("1m", 60 * 1000L),
    I_5M("5m", 5 * 60 * 1000L),
    I_15M("15m", 15 * 60 * 1000L),
    I_30M("30m", 30 * 60 * 1000L),
    I_1H("1h", 60 * 60 * 1000L),
    I_2H("2h", 2 * 60 * 60 * 1000L),
    I_4H("4h", 4 * 60 * 60 * 1000L),
    I_6H("6h", 6 * 60 * 60 * 1000L),
    I_8H("8h", 8 * 60 * 60 * 1000L),
    I_16H("16h", 16 * 60 * 60 * 1000L),
    I_1D("1d", 24 * 60 * 60 * 1000L);

    @Getter
    private String code;

    @Getter
    private long epochStep;

    IntervalConst(String code, long epochStep) {
      this.code = code;
      this.epochStep = epochStep;
    }

    public static IntervalConst fromCode(final String code) {
      return Arrays.stream(values()).filter(v -> v.code().equals(code)).findFirst().orElseThrow();
    }
  }

}
