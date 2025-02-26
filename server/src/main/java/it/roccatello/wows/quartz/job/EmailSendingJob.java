package it.roccatello.wows.quartz.job;

import org.apache.commons.lang3.BooleanUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.roccatello.wows.config.AppProperties;
import it.roccatello.wows.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class EmailSendingJob extends QuartzJobBean {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private EmailNotificationService emailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        if (BooleanUtils.isNotTrue(this.appProperties.getEmail())) {
            log.debug("Notification emails disabled");
            return;
        }
        log.debug("Processing notification emails...");

        try {
            this.emailService.sendQueuedEmails();
        } catch (Exception error) {
            log.error("Error executing background email sending: {}", error);
        }
    }

}
