package it.roccatello.wows.model.db;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Indicator extends BaseDbModel {
  private String code;
  private String name;
  private Boolean enabled;
}
