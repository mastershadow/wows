package it.roccatello.wows.quartz.job;

import java.util.Map;
import java.util.Optional;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.roccatello.wows.service.ProviderService;
import it.roccatello.wows.service.broker.BrokerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProvidersFetchingJob extends QuartzJobBean {

  @Autowired
  private ProviderService providerService;

  @Autowired
  private Map<String, BrokerService> brokerServices;

  private Optional<BrokerService> getBroker(String code) {
    return Optional.ofNullable(this.brokerServices.getOrDefault(code.toLowerCase() + "Service", null));
  }

  @PostConstruct
  private void configure() {
    providerService.getEnabledProviders().forEach(provider -> {
      getBroker(provider.getCode()).ifPresent(broker -> {
        broker.configure(provider);
      });
    });
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    providerService.getEnabledProviders().forEach(provider -> {
      getBroker(provider.getCode()).ifPresent(broker -> {
        log.info("{}", broker);
      });
    });
  }
}
