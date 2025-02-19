package it.roccatello.wows.quartz;

import java.util.UUID;

import org.quartz.SchedulerException;

public class InstanceIdGenerator implements org.quartz.spi.InstanceIdGenerator {

  @Override
  public String generateInstanceId() throws SchedulerException {
      try {
          return UUID.randomUUID().toString();
      } catch (Exception ex) {
          throw new SchedulerException("Couldn't generate UUID!", ex);
      }
  }

}


