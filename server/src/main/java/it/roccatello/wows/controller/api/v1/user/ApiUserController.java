package it.roccatello.wows.controller.api.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import it.roccatello.wows.controller.api.v1.V1ApiController;
import it.roccatello.wows.model.dto.DtoLogin;
import it.roccatello.wows.service.AuthenticationService;
import it.roccatello.wows.service.JwtService;

@Controller
@RequestMapping("/user")
public class ApiUserController extends V1ApiController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<String> authenticate(@RequestBody DtoLogin loginDto) {
    var authenticatedUser = this.authenticationService.authenticate(loginDto);
    return ResponseEntity.ok(this.jwtService.generateToken(authenticatedUser));
  }
}
