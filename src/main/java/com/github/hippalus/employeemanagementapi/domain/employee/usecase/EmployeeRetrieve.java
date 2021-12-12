package com.github.hippalus.employeemanagementapi.domain.employee.usecase;

import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;
import lombok.Value;

@Value
public class EmployeeRetrieve implements UseCase {

  Long employeeId;

}
