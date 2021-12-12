package com.github.hippalus.employeemanagementapi.domain.employee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
public class Employee {

  private final Long id;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String phoneNumber;
  private final Integer age;
  private final BigDecimal salary;
  private final EmployeeState state;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

}
