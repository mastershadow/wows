package it.roccatello.wows.repository;

import java.util.Optional;

import it.roccatello.wows.model.db.User;

public interface UserRepository extends BaseRepository<User> {
  Optional<User> findByEmail(String email);

  Optional<User> findByJwtToken(String token);

  Optional<User> findByEmailAndEnabled(String email, Boolean enabled);

  Optional<User> findByEmailAndActive(String email, Boolean active);

  Optional<User> findByEmailAndEnabledAndActive(String email, Boolean enabled, Boolean active);
}
