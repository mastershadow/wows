package it.roccatello.wows.quartz;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.roccatello.wows.config.AppProperties;
import it.roccatello.wows.service.SchedulerService;

@Component
@Profile("!test")
public class SchedulerStarter implements ApplicationRunner {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private AppProperties appProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (BooleanUtils.isTrue(this.appProperties.getScheduler())) {
            this.schedulerService.startJobs();
        }
    }

}
