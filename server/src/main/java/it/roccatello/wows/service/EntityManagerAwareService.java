package it.roccatello.wows.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class EntityManagerAwareService extends BaseService {
  @PersistenceContext
  protected EntityManager entityManager;

  protected <T> List<T> detach(List<T> list) {
    return list.stream().peek(this.entityManager::detach).toList();
  }

  protected <T> void detach(T o) {
    this.entityManager.detach(o);
  }
}
