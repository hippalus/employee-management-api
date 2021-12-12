package com.github.hippalus.employeemanagementapi.domain.common.exception;

public class DataNotFoundException extends EmployeeApiBusinessException {

  public DataNotFoundException(String key) {
    super(key);
  }

  public DataNotFoundException(String key, String... args) {
    super(key, args);
  }
}
