package it.roccatello.wows.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Provider;
import it.roccatello.wows.repository.ProviderRepository;

@Service
public class ProviderService extends EntityManagerAwareService {

  @Autowired
  private ProviderRepository repository;

  public List<Provider> enabledProviders() {
    return this.repository.findByEnabledTrue().stream().peek(this.entityManager::detach).toList();
  };

  public Optional<Provider> firstProvider() {
    return this.repository.findOneByEnabledTrue();
  };



}
