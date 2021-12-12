package com.github.hippalus.employeemanagementapi.domain.employee.usecase;

import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;
import lombok.Value;

@Value(staticConstructor = "valueOf")
public class EmployeeFinishSecurityCheck implements UseCase {

  Long employeeId;

  public String getEmployeeIdStr() {
    return employeeId.toString();
  }
}

