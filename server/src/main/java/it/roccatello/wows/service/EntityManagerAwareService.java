package it.roccatello.wows.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class EntityManagerAwareService {
  @PersistenceContext
  protected EntityManager entityManager;

}
