package it.roccatello.wows.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoLogin extends BaseDtoModel {
  private String email;
  private String password;
}
