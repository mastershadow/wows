package it.roccatello.wows.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.service.RestHttpService;

@Service
public class BitvavoService {

  @Autowired
  private RestHttpService httpService;

}
