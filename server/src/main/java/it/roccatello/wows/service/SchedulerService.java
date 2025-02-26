package it.roccatello.wows.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import it.roccatello.wows.quartz.SchedulableJobCreator;
import it.roccatello.wows.quartz.job.CleanupJob;
import it.roccatello.wows.quartz.job.EmailSendingJob;
import it.roccatello.wows.quartz.job.ProvidersFetchingJob;
import it.roccatello.wows.quartz.job.TelegramBotJob;

@Service
public class SchedulerService {
  private static final long SECOND = 1000L;
  private static final long FIFTEEN_SECONDS = 15 * SECOND;
  private static final long MINUTE = 60 * 1000L;
  private static final long FIVE_MINUTES = 5 * MINUTE;
  private static final long HOUR = 60 * 60 * 1000L;
  private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

  @Autowired
  private SchedulerFactoryBean schedulerFactoryBean;

  @Autowired
  private ApplicationContext context;

  @Autowired
  private SchedulableJobCreator jobCreator;

  private List<JobDetail> scheduledJobs = new ArrayList<JobDetail>();

  public void startJobs() {
    this.startProvidersFetchingJob();
    this.startEmailSendingJob();
    this.startTelegramBot();
    this.startCleanupJob();
  }

  public void stopJobs() {
    try {
      this.schedulerFactoryBean.getScheduler().clear();
      this.scheduledJobs.clear();
    } catch (SchedulerException e) {
      logger.error("{}", e);
    }
  }

  public boolean stopJob(JobDetail detail) {
    try {
      final boolean deleted = this.schedulerFactoryBean.getScheduler().deleteJob(detail.getKey());
      if (deleted) {
        this.scheduledJobs.remove(detail);
      }
      return deleted;
    } catch (SchedulerException e) {
      logger.error("{}", e);
    }
    return false;
  }

  public Date startJob(JobDetail jobDetail, Trigger trigger) {
    final Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
    try {
      final Date scheduleDate = scheduler.scheduleJob(jobDetail, trigger);
      if (scheduleDate != null) {
        this.scheduledJobs.add(jobDetail);
        return scheduleDate;
      }
    } catch (SchedulerException e) {
      logger.error("{}", e);
    }
    return null;
  }

  private void startProvidersFetchingJob() {
    final JobDetail details = this.jobCreator.createJob(ProvidersFetchingJob.class, true, context, "Sender", "Email");
    final SimpleTrigger trigger = this.jobCreator.createSimpleTrigger("ProvidersFetchingJobTrigger", new Date(),
        FIVE_MINUTES, Trigger.MISFIRE_INSTRUCTION_SMART_POLICY);
    this.startJob(details, trigger);

  }

  private void startEmailSendingJob() {
    final JobDetail details = this.jobCreator.createJob(EmailSendingJob.class, true, context, "Sender", "Email");
    final SimpleTrigger trigger = this.jobCreator.createSimpleTrigger("EmailJobTrigger", new Date(), FIFTEEN_SECONDS,
        Trigger.MISFIRE_INSTRUCTION_SMART_POLICY);
    this.startJob(details, trigger);
  }

  private void startTelegramBot() {
    final JobDetail details = this.jobCreator.createJob(TelegramBotJob.class, true, context, "Sender", "Push");
    final SimpleTrigger trigger = this.jobCreator.createSimpleTrigger("PushJobTrigger", new Date(), FIFTEEN_SECONDS,
        Trigger.MISFIRE_INSTRUCTION_SMART_POLICY);
    this.startJob(details, trigger);
  }

  private void startCleanupJob() {
    final JobDetail details = this.jobCreator.createJob(CleanupJob.class, true, context, "Cleanup", "Cleanup");
    final SimpleTrigger trigger = this.jobCreator.createSimpleTrigger("CleanupJobTrigger", new Date(), HOUR,
        Trigger.MISFIRE_INSTRUCTION_SMART_POLICY);
    this.startJob(details, trigger);
  }
}
