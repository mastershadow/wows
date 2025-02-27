package it.roccatello.wows.repository;

import it.roccatello.wows.model.db.Provider;
import java.util.List;
import java.util.Optional;


public interface ProviderRepository extends BaseRepository<Provider> {
  List<Provider> findByEnabledTrue();
  Optional<Provider> findOneByEnabledTrue();
}
