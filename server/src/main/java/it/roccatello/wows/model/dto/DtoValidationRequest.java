package it.roccatello.wows.model.dto;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoValidationRequest extends BaseDtoModel {
  private Long id;
  private Map<String, Object> fields;
}
