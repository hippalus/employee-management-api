package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeCreateUseCaseHandler implements UseCaseHandler<Employee, EmployeeCreate> {

  private final EmployeePort employeePort;

  @Override
  public Employee handle(EmployeeCreate useCase) {
    return employeePort.create(useCase.toModel());
  }
}
