package it.roccatello.wows.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoDataFilter extends BaseDtoModel {
  private String queryString;
}
