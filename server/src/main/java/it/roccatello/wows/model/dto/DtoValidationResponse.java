package it.roccatello.wows.model.dto;

import java.util.HashMap;
import java.util.Map;

public class DtoValidationResponse {
  private boolean valid;
  private Map<String, String> fields;

  public DtoValidationResponse() {
    this.valid = false;
    this.fields = new HashMap<String, String>();
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public void setFields(Map<String, String> fields) {
    this.fields = fields;
  }

}
