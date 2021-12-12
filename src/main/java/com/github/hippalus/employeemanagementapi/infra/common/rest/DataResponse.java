package com.github.hippalus.employeemanagementapi.infra.common.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {

  private List<T> items = List.of();
  private int page;
  private int size;
  private long totalSize;

  public DataResponse(List<T> items) {
    this.items = items;
  }

  public List<T> getItems() {
    return items;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getSize() {
    return size;
  }

  public Long getTotalSize() {
    return totalSize;
  }

}
