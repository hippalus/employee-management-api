package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.ObservableUseCasePublisher;
import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeRecheck;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRecheckUseCaseHandler extends ObservableUseCasePublisher implements
    UseCaseHandler<Employee, EmployeeRecheck> {

  private final EmployeePort employeePort;
  private final EmployeeStateService employeeStateService;

  public EmployeeRecheckUseCaseHandler(EmployeePort employeePort,
      EmployeeStateService employeeStateService) {
    this.employeePort = employeePort;
    this.employeeStateService = employeeStateService;
    register(EmployeeRecheck.class, this);
  }

  @Override
  public Employee handle(EmployeeRecheck useCase) {
    final Long employeeId = useCase.getEmployeeId();
    EmployeeState lastState = employeePort.getState(employeeId);
    final var stateMachine = employeeStateService.getStateMachine(useCase.getEmployeeIdStr(), lastState);
    final var msg = MessageBuilder.withPayload(EmployeeEventType.RECHECK)
        .setHeader("employee_id", employeeId)
        .build();
    stateMachine.sendEvent(msg);
    return employeePort.retrieve(employeeId);
  }


}
