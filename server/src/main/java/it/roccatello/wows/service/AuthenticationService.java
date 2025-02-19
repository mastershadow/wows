package it.roccatello.wows.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.User;
import it.roccatello.wows.model.dto.LoginDto;
import it.roccatello.wows.repository.UserRepository;

@Service
public class AuthenticationService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  public User authenticate(LoginDto input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

    return userRepository.findByEmail(input.getEmail())
        .orElseThrow();
  }

}
