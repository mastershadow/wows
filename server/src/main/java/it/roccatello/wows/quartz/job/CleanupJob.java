package it.roccatello.wows.quartz.job;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.roccatello.wows.config.AppProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class CleanupJob extends QuartzJobBean {

  @Autowired
  private AppProperties appProperties;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    log.debug("Cleanup dangling temp files...");
    var tmpDir = this.appProperties.getTmpPath();
    if (tmpDir.exists()) {
      var timeRef = Instant.now().minusSeconds(60 * 60 * 24);
      for (File file : tmpDir.listFiles()) {
        try {
          BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
          if (attr.creationTime().toInstant().isBefore(timeRef)) {
            file.delete();
          }
        } catch (IOException ex) {
          log.error("Error during temporary file deletion {}: {}", file.getName(), ex);
        }
      }
    }
  }
}
