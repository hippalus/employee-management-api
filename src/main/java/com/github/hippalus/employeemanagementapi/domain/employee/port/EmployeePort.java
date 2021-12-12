package com.github.hippalus.employeemanagementapi.domain.employee.port;

import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeePort {

  Employee create(Employee employee);

  Employee retrieve(Long employeeId);

  boolean exists(Long employeeId);

  Page<Employee> retrieve(Pageable pageable);

  void updateState(Long employeeId, EmployeeState employeeState);

  EmployeeState getState(Long employeeId);
}
