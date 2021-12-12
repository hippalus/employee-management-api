package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.ObservableUseCasePublisher;
import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeInCheck;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeInCheckUseCaseHandler extends ObservableUseCasePublisher implements
    UseCaseHandler<Employee, EmployeeInCheck> {

  private final EmployeePort employeePort;
  private final EmployeeStateService employeeStateService;

  public EmployeeInCheckUseCaseHandler(EmployeePort employeePort,
      EmployeeStateService employeeStateService) {
    this.employeePort = employeePort;
    this.employeeStateService = employeeStateService;
    register(EmployeeInCheck.class, this);
  }

  @Override
  @Transactional
  public Employee handle(EmployeeInCheck useCase) {
    final Long employeeId = useCase.getEmployeeId();
    EmployeeState lastState = employeePort.getState(employeeId);
    final var stateMachine = employeeStateService.getStateMachine(useCase.getEmployeeIdStr(), lastState);
    final var msg = MessageBuilder.withPayload(EmployeeEventType.READY_TO_CHECK)
        .setHeader("employee_id", employeeId)
        .build();
    stateMachine.sendEvent(msg);
    return employeePort.retrieve(employeeId);
  }

}
