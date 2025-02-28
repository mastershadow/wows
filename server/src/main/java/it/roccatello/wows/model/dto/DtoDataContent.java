package it.roccatello.wows.model.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DtoDataContent<T> extends BaseDtoModel {
  private List<T> data;
  private boolean next;
  private int current;
  private long total;

  public DtoDataContent() {
  }

  public DtoDataContent(List<T> data, boolean next, int current, long total) {
    this.data = data;
    this.next = next;
    this.total = total;
    this.current = current;
  }

}
