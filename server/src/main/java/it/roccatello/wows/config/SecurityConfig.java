package it.roccatello.wows.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import it.roccatello.wows.config.filter.JwtAuthenticationFilter;
import it.roccatello.wows.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
  public static final String ROLE_ADMINISTRATOR = "AMINISTRATOR";
  public static final String ROLE_USER = "USER";

  private final String[] WHITE_LIST = new String[] { //
      "/error",
      "/api/v1/user/login",
      "/robots.txt", //
      "/assets/**", //
      "/css/**", //
      "/js/**", //
      "/fonts/**", //
      "/img/**", //
      "/webjars/**",
      "/favicon.ico",
      "/*.html",
      "/*.js",
      "/*.css",
      "/manifest.webmanifest",
      "/ngsw.json"
  };

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private AppProperties appProperties;

  @Bean
  UserDetailsService userDetailsService() {
      return username -> this.userRepository.findByEmail(username)
              .orElseThrow(() -> new UsernameNotFoundException("No user"));
  }

  @Bean
  AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder
      .userDetailsService(userDetailsService())
      .passwordEncoder(passwordEncoder());
    return authenticationManagerBuilder.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .headers(c -> 
          c.frameOptions(fo -> fo.sameOrigin())
        )
        .csrf(c -> c.disable())
        .cors(t -> 
          t.configurationSource(this.corsConfigurationSource())
        )
        .sessionManagement(sm -> 
          sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(ar -> 
          ar.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers(this.WHITE_LIST).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.applyPermitDefaultValues();
    corsConfiguration.setAllowedHeaders(List.of("Authorization","Content-Type"));
    corsConfiguration.setAllowedOrigins(List.of(appProperties.getFrontendUrl()));
    corsConfiguration.setAllowedMethods(Collections.unmodifiableList(
        Arrays.asList(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name(),
            HttpMethod.POST.name())));
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}
