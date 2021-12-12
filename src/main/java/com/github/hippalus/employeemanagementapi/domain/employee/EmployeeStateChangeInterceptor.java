package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import com.github.hippalus.employeemanagementapi.domain.employee.port.EmployeePort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeStateChangeInterceptor extends StateMachineInterceptorAdapter<EmployeeState, EmployeeEventType> {

  private final EmployeePort employeePort;

  @Override
  public void preStateChange(
      State<EmployeeState, EmployeeEventType> states,
      Message<EmployeeEventType> messages,
      Transition<EmployeeState, EmployeeEventType> transitions,
      StateMachine<EmployeeState, EmployeeEventType> stateMachine,
      StateMachine<EmployeeState, EmployeeEventType> rootStateMachine) {

    Optional.ofNullable(messages)
        .flatMap(msg -> Optional.ofNullable((Long) msg.getHeaders().getOrDefault("employee_id", -1L)))
        .ifPresent(employeeId -> employeePort.updateState(employeeId, states.getId()));
  }


}
