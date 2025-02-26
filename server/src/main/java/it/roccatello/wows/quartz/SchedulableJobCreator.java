package it.roccatello.wows.quartz;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulableJobCreator {
  private static final Logger logger = LoggerFactory.getLogger(SchedulableJobCreator.class);

  public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable, ApplicationContext context, String jobName, String jobGroup) {
    final JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(jobClass);
    factoryBean.setDurability(isDurable);
    factoryBean.setApplicationContext(context);
    factoryBean.setName(jobName);
    factoryBean.setGroup(jobGroup);

    final JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put(jobName + jobGroup, jobClass.getName());
    factoryBean.setJobDataMap(jobDataMap);
    factoryBean.afterPropertiesSet();

    return factoryBean.getObject();
  }

  public CronTrigger createCronTrigger(String triggerName, Date startTime, String cronExpression, int misFireInstruction) {
    final CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
    factoryBean.setName(triggerName);
    factoryBean.setStartTime(startTime);
    factoryBean.setCronExpression(cronExpression);
    factoryBean.setMisfireInstruction(misFireInstruction);

    try {
      factoryBean.afterPropertiesSet();
    } catch (ParseException e) {
      logger.error("{}", e);
    }
    return factoryBean.getObject();
  }

  public SimpleTrigger createSimpleTrigger(String triggerName, Date startTime, Long repeatTime,
      int misFireInstruction) {
    final SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName(triggerName);
    factoryBean.setStartTime(startTime);
    factoryBean.setRepeatInterval(repeatTime);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(misFireInstruction);
    factoryBean.afterPropertiesSet();
    return factoryBean.getObject();
  }
}
