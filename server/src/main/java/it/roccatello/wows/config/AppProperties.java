package it.roccatello.wows.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("app")
public class AppProperties {
  private Boolean email = false;
  private String emailFrom;
  private Boolean scheduler = false;
  private String frontendUrl;
  private String logPath;
  private String tmpPath;
}
