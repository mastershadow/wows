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
        getBroker(provider.getCode()).ifPresent(

          broker -> {
            log.debug("Candle fetching on provider: {}", broker.getCode());  
            this.tickerService.enabledTickers().forEach(
              
              ticker -> {
                var req = new BrokerCandleRequest(ticker.getTicker(), null);
                log.debug("Fetching ticker: {}", req);  
                var res = broker.fetchCandles(req);
                if (res != null) {

                }
              });
          });
    });
  }
}
