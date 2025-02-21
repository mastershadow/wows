package it.roccatello.wows.model.db;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Interval extends BaseDbModel {
  private String code;
}
