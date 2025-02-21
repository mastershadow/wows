package it.roccatello.wows.model.db;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Trend extends BaseDbModel {
  @ManyToOne
  private Ticker user;

  @ManyToOne
  private TrendType type;

  @ManyToOne
  private Indicator indicator;

  private LocalDateTime occurred;
  
  private LocalDateTime ended;
}
