package it.roccatello.wows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class,
    UserDetailsServiceAutoConfiguration.class })
@ConfigurationPropertiesScan("it.roccatello.wows")
public class GeneratePasswordApplication implements ApplicationRunner {

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  private ConfigurableApplicationContext context;

  public static void main(String[] args) {
    SpringApplication.run(GeneratePasswordApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (args.containsOption("password")) {
      log.info("Save this password into your user");
      log.info(this.encoder.encode(args.getOptionValues("password").get(0)));
      this.context.close();
    }
  }
}
