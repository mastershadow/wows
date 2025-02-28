package it.roccatello.wows.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class EntityManagerAwareService extends BaseService {
  @PersistenceContext
  protected EntityManager entityManager;

}
