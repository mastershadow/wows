package it.roccatello.wows.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("jwt")
public class JwtProperties {
  private String authUrl;
  private String secret;
  private Long expiration;
  private Long resetExpiration;
  private String issuer;
  private String audience;
}
