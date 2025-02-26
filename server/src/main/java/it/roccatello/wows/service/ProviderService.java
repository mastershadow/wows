package it.roccatello.wows.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Provider;
import it.roccatello.wows.repository.ProviderRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProviderService {
  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private ProviderRepository repository;

  private List<Provider> enabledProviders;

  @PostConstruct
  private void postConstruct() {
    this.enabledProviders = this.repository.findByEnabledTrue().stream().peek(this.entityManager::detach).toList();
  }

  public List<Provider> getEnabledProviders() {
    return enabledProviders;
  };

}
