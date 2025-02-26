package it.roccatello.wows.repository;

import it.roccatello.wows.model.db.Provider;
import java.util.List;


public interface ProviderRepository extends BaseRepository<Provider> {
  List<Provider> findByEnabledTrue();
}
