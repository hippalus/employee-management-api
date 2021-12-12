package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeStateService {

  private final StateMachineFactory<EmployeeState, EmployeeEventType> factory;
  private final EmployeeStateChangeInterceptor changeInterceptor;

  public StateMachine<EmployeeState, EmployeeEventType> getStateMachine(final String stateMachineId, final EmployeeState state) {
    final var stateMachine = factory.getStateMachine(stateMachineId);
    stateMachine.stop();
    stateMachine.getStateMachineAccessor()
        .doWithAllRegions(sma -> {
          sma.addStateMachineInterceptor(changeInterceptor);
          sma.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null));
        });
    stateMachine.start();
    return stateMachine;
  }
}
