package it.roccatello.wows.model.db;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token extends BaseDbModel {
  private String token;
  @ColumnDefault("false")
  private Boolean revoked;

  private LocalDateTime expiresAt;

  @ManyToOne
  private User user;
}
