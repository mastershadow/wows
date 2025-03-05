package it.roccatello.wows.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DataFetch extends BaseDbModel {
  @ManyToOne(fetch = FetchType.LAZY)
  private Ticker ticker;
  @ManyToOne(fetch = FetchType.LAZY)
  private Interval interval;
  private Long occurred;
  private Boolean processed = false;
}
