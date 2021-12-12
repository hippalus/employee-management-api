package com.github.hippalus.employeemanagementapi.infra.adapters.employee.rest.dto;

import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeCreate;
import java.math.BigDecimal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequest {

  @NotNull
  @NotEmpty
  @NotBlank
  private String firstName;
  @NotNull
  @NotEmpty
  @NotBlank
  private String lastName;
  @Email
  @NotNull
  private String email;
  @Pattern(regexp = "(^$|[0-9]{10})")
  private String phoneNumber;
  @NotNull
  @Positive
  private Integer age;
  @Positive
  private BigDecimal salary;

  public EmployeeCreate toUseCase() {
    return EmployeeCreate.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .phoneNumber(phoneNumber)
        .age(age)
        .salary(salary)
        .build();
  }

}
