package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.ObservableUseCasePublisher;
import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeApprove;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeApproveUseCaseHandler extends ObservableUseCasePublisher implements
    UseCaseHandler<Employee, EmployeeApprove> {

  private final EmployeePort employeePort;
  private final EmployeeStateService employeeStateService;

  public EmployeeApproveUseCaseHandler(EmployeePort employeePort,
      EmployeeStateService employeeStateService) {
    this.employeePort = employeePort;
    this.employeeStateService = employeeStateService;
    register(EmployeeApprove.class, this);
  }

  @Override
  @Transactional
  public Employee handle(EmployeeApprove useCase) {
    final Long employeeId = useCase.getEmployeeId();
    final var state = employeePort.getState(employeeId);
    final var stateMachine = employeeStateService.getStateMachine(useCase.getEmployeeIdStr(), state);
    final var msg = MessageBuilder.withPayload(EmployeeEventType.APPROVED)
        .setHeader("employee_id", employeeId)
        .build();
    stateMachine.sendEvent(msg);
    return employeePort.retrieve(employeeId);
  }
}
