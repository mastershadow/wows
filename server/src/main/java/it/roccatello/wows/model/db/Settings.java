package it.roccatello.wows.model.db;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "settings")
@Getter
@Setter
public class Settings extends BaseDbModel {
  @Column(unique = true, length = 100, nullable = false)
  private String code;

  private String value;
}
