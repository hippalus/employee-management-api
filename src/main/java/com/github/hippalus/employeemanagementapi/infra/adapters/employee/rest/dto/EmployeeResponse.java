package com.github.hippalus.employeemanagementapi.infra.adapters.employee.rest.dto;

import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private Integer age;
  private BigDecimal salary;
  private String state;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static EmployeeResponse fromModel(Employee employee) {
    return EmployeeResponse.builder()
        .id(employee.getId())
        .age(employee.getAge())
        .createdAt(employee.getCreatedAt())
        .salary(employee.getSalary())
        .state(employee.getState().name())
        .updatedAt(employee.getUpdatedAt())
        .age(employee.getAge())
        .phoneNumber(employee.getPhoneNumber())
        .firstName(employee.getFirstName())
        .lastName(employee.getLastName())
        .build();
  }
}
