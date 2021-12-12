package com.github.hippalus.employeemanagementapi.infra.adapters.employee.jpa.entity;

import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import com.github.hippalus.employeemanagementapi.infra.common.entity.AbstractAuditingEntity;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity extends AbstractAuditingEntity {

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private Integer age;

  @Column
  private BigDecimal salary;

  @Enumerated(EnumType.STRING)
  private EmployeeState state;

/*  @ElementCollection(targetClass = EmployeeState.class)
  @JoinTable(name = "employee_state", joinColumns = @JoinColumn(name = "employee_id"))
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Set<EmployeeState> state = Set.of();*/

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    EmployeeEntity that = (EmployeeEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public Employee toModel() {
    return Employee.builder()
        .id(super.getId())
        .firstName(getFirstName())
        .lastName(getLastName())
        .email(getEmail())
        .age(getAge())
        .salary(getSalary())
        .state(getState())
        .phoneNumber(getPhoneNumber())
        .createdAt(getCreatedAt())
        .updatedAt(getUpdatedAt())
        .build();
  }
}
