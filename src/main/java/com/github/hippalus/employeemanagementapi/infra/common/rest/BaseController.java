package com.github.hippalus.employeemanagementapi.infra.common.rest;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.ObservableUseCasePublisher;
import java.util.List;

public class BaseController extends ObservableUseCasePublisher {

  public <T> Response<DataResponse<T>> respond(List<T> items) {
    return ResponseBuilder.build(items);
  }

  public <T> Response<DataResponse<T>> respond(List<T> items, int page, int size, Long totalSize) {
    return ResponseBuilder.build(items, page, size, totalSize);
  }

  protected <T> Response<T> respond(T item) {
    return ResponseBuilder.build(item);
  }

  protected Response<ErrorResponse> respond(ErrorResponse errorResponse) {
    return ResponseBuilder.build(errorResponse);
  }
}
