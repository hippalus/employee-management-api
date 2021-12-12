package com.github.hippalus.employeemanagementapi.domain.common.exception;

import lombok.Getter;

@Getter
public class EmployeeApiBusinessException extends RuntimeException {

  private final String key;
  private final String[] args;

  public EmployeeApiBusinessException(String key) {
    super(key);
    this.key = key;
    args = new String[0];
  }

  public EmployeeApiBusinessException(String key, String... args) {
    super(key);
    this.key = key;
    this.args = args;
  }

}
