package com.github.hippalus.employeemanagementapi.domain.employee.usecase;

import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;
import lombok.Value;

@Value(staticConstructor = "valueOf")
public class EmployeeRetrieve implements UseCase {

  Long employeeId;

}
