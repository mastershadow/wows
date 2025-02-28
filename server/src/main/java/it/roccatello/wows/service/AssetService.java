package it.roccatello.wows.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.roccatello.wows.model.db.Asset;
import it.roccatello.wows.model.dto.DtoAsset;
import it.roccatello.wows.repository.AssetRepository;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Accessors(fluent = true)
public class AssetService extends BaseService {

  @Autowired
  private AssetRepository repository;


  public List<DtoAsset> assets() {
    return this.repository.findAll().stream().map(this::convertToDto).toList();
  }

	private DtoAsset convertToDto(Asset a) {
    var dto = new DtoAsset();
    dto.setId(a.getId());
    dto.setAmount(a.getAmount());
    dto.setTicker(a.getTicker().getTicker());
    dto.setProviderCode(a.getProvider().getCode());
    dto.setProviderId(a.getProvider().getId());
    return dto;
	}
}
