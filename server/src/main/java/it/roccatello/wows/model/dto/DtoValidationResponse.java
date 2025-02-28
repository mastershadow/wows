package it.roccatello.wows.model.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoValidationResponse extends BaseDtoModel {
  private boolean valid;
  private Map<String, String> fields;

  public DtoValidationResponse() {
    this.valid = false;
    this.fields = new HashMap<String, String>();
  }
}
