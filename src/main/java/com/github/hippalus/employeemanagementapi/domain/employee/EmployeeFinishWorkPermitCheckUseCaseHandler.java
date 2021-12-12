package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.common.usecase.UseCaseHandler;
import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.Employee;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import com.github.hippalus.employeemanagementapi.domain.employee.usecase.EmployeeFinishWorkPermitCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeFinishWorkPermitCheckUseCaseHandler implements UseCaseHandler<Employee, EmployeeFinishWorkPermitCheck> {

  private final EmployeePort employeePort;
  private final EmployeeStateService employeeStateService;

  @Override
  public Employee handle(EmployeeFinishWorkPermitCheck useCase) {
    final Long employeeId = useCase.getEmployeeId();
    final var state = employeePort.getState(employeeId);
    final var stateMachine = employeeStateService.getStateMachine(useCase.getEmployeeIdStr(), state);
    final var msg = MessageBuilder.withPayload(EmployeeEventType.WORK_PERMIT_CHECK_FINISHED)
        .setHeader("employee_id", employeeId)
        .build();
    stateMachine.sendEvent(msg);
    return employeePort.retrieve(employeeId);
  }
}
