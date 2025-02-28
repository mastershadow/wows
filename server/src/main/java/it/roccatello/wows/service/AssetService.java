package it.roccatello.wows.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.repository.AssetRepository;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Accessors(fluent = true)
public class AssetService {

  @Autowired
  private AssetRepository repository;

  
}
