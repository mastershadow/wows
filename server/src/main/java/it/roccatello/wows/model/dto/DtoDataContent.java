package it.roccatello.wows.model.dto;

import java.util.List;

public class DtoDataContent<T> {
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

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public boolean isNext() {
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getCurrent() {
    return current;
  }

  public void setCurrent(int current) {
    this.current = current;
  }

}
