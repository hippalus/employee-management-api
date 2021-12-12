package com.github.hippalus.employeemanagementapi.infra.common.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Response<T> {

  private T data;
  private ErrorResponse errors;

  public Response(ErrorResponse errors) {
    this.errors = errors;
  }

  public Response(T data) {
    this.data = data;
  }

}
