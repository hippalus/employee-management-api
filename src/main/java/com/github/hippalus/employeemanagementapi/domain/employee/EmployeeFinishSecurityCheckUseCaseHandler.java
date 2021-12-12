package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.ObservableUseCasePublisher;
import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeFinishSecurityCheck;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeFinishSecurityCheckUseCaseHandler extends ObservableUseCasePublisher implements
    UseCaseHandler<Employee, EmployeeFinishSecurityCheck> {

  private final EmployeePort employeePort;
  private final EmployeeStateService employeeStateService;

  public EmployeeFinishSecurityCheckUseCaseHandler(EmployeePort employeePort,
      EmployeeStateService employeeStateService) {
    this.employeePort = employeePort;
    this.employeeStateService = employeeStateService;
    register(EmployeeFinishSecurityCheck.class, this);
  }

  @Override
  public Employee handle(EmployeeFinishSecurityCheck useCase) {
    final Long employeeId = useCase.getEmployeeId();
    final var state = employeePort.getState(employeeId);
    final var stateMachine = employeeStateService.getStateMachine(useCase.getEmployeeIdStr(), state);
    final var msg = MessageBuilder.withPayload(EmployeeEventType.SECURITY_CHECK_FINISHED)
        .setHeader("employee_id", employeeId)
        .build();
    stateMachine.sendEvent(msg);
    return employeePort.retrieve(employeeId);
  }
}
