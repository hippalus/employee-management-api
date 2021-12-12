package com.github.hippalus.employeemanagementapi.infra.common.rest;

import java.util.List;

public class ResponseBuilder {

  private ResponseBuilder() {
  }

  public static <T> Response<DataResponse<T>> build(List<T> items) {
    return new Response<>(new DataResponse<>(items));
  }

  public static <T> Response<DataResponse<T>> build(List<T> items, int page, int size, long totalSize) {
    return new Response<>(new DataResponse<>(items, page, size, totalSize));
  }

  public static <T> Response<T> build(T item) {
    return new Response<>(item);
  }

  public static Response<ErrorResponse> build(ErrorResponse errorResponse) {
    return new Response<>(errorResponse);
  }
}
