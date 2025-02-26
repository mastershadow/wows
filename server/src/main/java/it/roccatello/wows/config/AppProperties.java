package it.roccatello.wows.config;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("app")
public class AppProperties {
  private Boolean email = false;
  private String emailFrom;
  private String emailTo;
  private Boolean scheduler = false;
  private String frontendUrl;
  private Boolean bot = false;
  private String botChatId;
  private String botToken;

  @NotEmpty
  private File logPath;
  @NotEmpty
  private File tmpPath;
}
