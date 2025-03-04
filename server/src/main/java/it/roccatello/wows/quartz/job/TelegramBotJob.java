package it.roccatello.wows.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.roccatello.wows.config.AppProperties;
import it.roccatello.wows.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;

@Slf4j
@DisallowConcurrentExecution
public class TelegramBotJob extends QuartzJobBean {
  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private NotificationService notificationService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
  }
}
