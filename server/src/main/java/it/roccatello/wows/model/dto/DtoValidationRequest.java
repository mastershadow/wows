package it.roccatello.wows.model.dto;

import java.util.Map;

public class DtoValidationRequest {
  private Long id;
  private Map<String, Object> fields;

  public Map<String, Object> getFields() {
    return fields;
  }

  public void setFields(Map<String, Object> fields) {
    this.fields = fields;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
