package it.roccatello.wows.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginDto extends BaseDtoModel {
  private String email;
  private String password;
}
