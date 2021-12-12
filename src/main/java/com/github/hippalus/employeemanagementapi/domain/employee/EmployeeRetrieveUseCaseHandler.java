package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.ObservableUseCasePublisher;
import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeRetrieve;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRetrieveUseCaseHandler extends ObservableUseCasePublisher implements
    UseCaseHandler<Employee, EmployeeRetrieve> {

  private final EmployeePort employeePort;

  public EmployeeRetrieveUseCaseHandler(EmployeePort employeePort) {
    this.employeePort = employeePort;
    register(EmployeeRetrieve.class, this);
  }

  @Override
  public Employee handle(EmployeeRetrieve useCase) {
    return employeePort.retrieve(useCase.getEmployeeId());
  }
}
