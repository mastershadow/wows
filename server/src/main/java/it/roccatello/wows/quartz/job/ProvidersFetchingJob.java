package it.roccatello.wows.quartz.job;

import java.util.Map;
import java.util.Optional;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.roccatello.wows.service.TickerService;
import it.roccatello.wows.model.data.BrokerCandleRequest;
import it.roccatello.wows.model.db.Interval;
import it.roccatello.wows.service.CandleService;
import it.roccatello.wows.service.IntervalService;
import it.roccatello.wows.service.ProviderService;
import it.roccatello.wows.service.broker.BrokerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class ProvidersFetchingJob extends QuartzJobBean {

  @Autowired
  private ProviderService providerService;

  @Autowired
  private TickerService tickerService;

  @Autowired
  private CandleService candleService;

  @Autowired
  private IntervalService intervalService;

  @Autowired
  private Map<String, BrokerService> brokerServices;

  private Optional<BrokerService> getBroker(String code) {
    return Optional.ofNullable(this.brokerServices.getOrDefault(code.toLowerCase() + "Service", null));
  }

  @PostConstruct
  private void configure() {
    this.providerService.enabledProviders().forEach(
        provider -> {
          getBroker(provider.getCode())
              .ifPresent(broker -> broker.configure(provider));
        });
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    this.providerService.firstProvider().ifPresent(
        provider -> {
          if (!provider.getFetchData()) {
            return;
          }

          getBroker(provider.getCode()).ifPresent(

              broker -> {
                if (!broker.enabled()) {
                  return;
                }
                
                log.debug("Candle fetching on provider: {}", broker.code());
                this.tickerService.enabledTickers().forEach(
                    ticker -> {
                      for (Interval interval : this.intervalService.enabledIntervals()) {
                        var req = new BrokerCandleRequest(ticker.getTicker(), interval.getCode());
                        log.debug("Fetching ticker: {}", req);
                        var res = broker.fetchCandles(req);
                        if (res != null) {
                          this.candleService.save(res);
                        }
                      }
                    });
              });
        });
  }
}
