package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeRetrieve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRetrieveUseCaseHandler implements UseCaseHandler<Employee, EmployeeRetrieve> {

  private final EmployeePort employeePort;

  @Override
  public Employee handle(EmployeeRetrieve useCase) {
    return employeePort.retrieve(useCase.getEmployeeId());
  }
}
