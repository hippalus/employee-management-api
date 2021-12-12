package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.ObservableUseCasePublisher;
import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeCreate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCreateUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<Employee, EmployeeCreate> {

  private final EmployeePort employeePort;

  public EmployeeCreateUseCaseHandler(EmployeePort employeePort) {
    this.employeePort = employeePort;
    register(EmployeeCreate.class, this);
  }

  @Override
  public Employee handle(EmployeeCreate useCase) {
    return employeePort.create(useCase.toModel());
  }
}
