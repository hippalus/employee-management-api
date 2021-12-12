package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeRetrieveAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeRetrieveAllUseCaseHandler implements UseCaseHandler<Page<Employee>, EmployeeRetrieveAll> {

  private final EmployeePort employeePort;

  @Override
  public Page<Employee> handle(EmployeeRetrieveAll useCase) {
    return employeePort.retrieve(PageRequest.of(useCase.getPage(), useCase.getSize()));
  }
}
