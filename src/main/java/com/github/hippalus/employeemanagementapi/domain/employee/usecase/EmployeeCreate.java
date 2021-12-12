package com.github.hippalus.employeemanagementapi.domain.employee.usecase;

import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreate implements UseCase {

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private Integer age;
  private BigDecimal salary;

  public Employee toModel() {
    return Employee.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .phoneNumber(phoneNumber)
        .age(age)
        .salary(salary)
        .state(EmployeeState.ADDED)
        .build();
  }
}
