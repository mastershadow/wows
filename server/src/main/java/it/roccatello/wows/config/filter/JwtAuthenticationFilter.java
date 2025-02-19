package it.roccatello.wows.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import it.roccatello.wows.service.JwtService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String BEARER = "Bearer ";
  private static final String AUTHORIZATION = "Authorization";

  @Autowired
  private HandlerExceptionResolver handlerExceptionResolver;

  @Autowired
  private JwtService jwtService;

  @Autowired
  @Lazy
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith(BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null) {
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null) {
          UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

          if (jwtService.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);
    }
  }
}
