package com.github.hippalus.employeemanagementapi.infra.adapters.employee.jpa;

import com.github.hippalus.employeemanagementapi.domain.common.exception.DataNotFoundException;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.infra.adapters.employee.jpa.entity.EmployeeEntity;
import com.github.hippalus.employeemanagementapi.infra.adapters.employee.jpa.repository.EmployeeJpaRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeJpaAdapter implements EmployeePort {

  private final EmployeeJpaRepository employeeJpaRepository;

  @Override
  public Employee create(Employee employee) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setFirstName(employee.getFirstName());
    employeeEntity.setLastName(employee.getLastName());
    employeeEntity.setEmail(employee.getEmail());
    employeeEntity.setAge(employee.getAge());
    employeeEntity.setSalary(employee.getSalary());
    employeeEntity.setState(EmployeeState.ADDED);
    employeeEntity.setPhoneNumber(employee.getPhoneNumber());
    return employeeJpaRepository.save(employeeEntity).toModel();

  }


  @Override
  public Employee retrieve(Long employeeId) {
    return employeeJpaRepository.findById(employeeId)
        .map(EmployeeEntity::toModel)
        .orElseThrow(() -> new DataNotFoundException("common.system.data.notFound", String.valueOf(employeeId)));
  }

  @Override
  public Page<Employee> retrieve(Pageable pageable) {
    final Page<EmployeeEntity> all = employeeJpaRepository.findAll(pageable);
    final List<Employee> employees = all
        .stream()
        .map(EmployeeEntity::toModel)
        .collect(Collectors.toList());
    return new PageImpl<>(employees, pageable, all.getTotalElements());
  }

  @Override
  public boolean exists(Long employeeId) {
    return employeeJpaRepository.existsById(employeeId);
  }

  @Override
  public void updateState(Long employeeId, EmployeeState employeeState) {
    if (!exists(employeeId)) {
      throw new DataNotFoundException("common.system.data.notFound", String.valueOf(employeeId));
    }
    employeeJpaRepository.updateState(employeeId, employeeState);
  }

  @Override
  public EmployeeState getState(Long employeeId) {
    ifNotExistsThrowEx(employeeId);
    return employeeJpaRepository.getStateById(employeeId);
  }

  private void ifNotExistsThrowEx(Long employeeId) {
    if (!exists(employeeId)) {
      throw new DataNotFoundException("common.system.data.notFound", String.valueOf(employeeId));
    }
  }
}
