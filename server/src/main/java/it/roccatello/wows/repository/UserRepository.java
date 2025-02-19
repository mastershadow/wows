package it.roccatello.wows.repository;

import java.util.Optional;

import it.roccatello.wows.model.db.User;

public interface UserRepository extends BaseRepository<User> {
  Optional<User> findByEmail(String email);
}
