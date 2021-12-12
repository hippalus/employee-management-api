package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeActivate;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeActivateUseCaseHandler implements UseCaseHandler<Employee, EmployeeActivate> {

  private final EmployeePort employeePort;
  private final EmployeeStateService employeeStateService;

  @Override
  @Transactional
  public Employee handle(EmployeeActivate useCase) {
    final Long employeeId = useCase.getEmployeeId();
    final var lastState = employeePort.getState(employeeId);
    final var stateMachine = employeeStateService.getStateMachine(useCase.getEmployeeIdStr(), lastState);
    final var msg = MessageBuilder.withPayload(EmployeeEventType.ACTIVATED)
        .setHeader("employee_id", employeeId)
        .build();
    stateMachine.sendEvent(msg);
    return employeePort.retrieve(employeeId);
  }
}
