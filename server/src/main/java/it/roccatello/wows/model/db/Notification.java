package it.roccatello.wows.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification extends BaseDbModel {
  @ManyToOne
  private User user;

  @ManyToOne
  private NotificationType type;

  private String event;

  private String content;
}
