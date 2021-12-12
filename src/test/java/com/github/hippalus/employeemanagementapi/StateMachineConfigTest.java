package com.github.hippalus.employeemanagementapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest
class StateMachineConfigTest {

  @Autowired
  private StateMachineFactory<EmployeeState, EmployeeEventType> factory;

  @Test
  void testStateTransitions() {
    final StateMachine<EmployeeState, EmployeeEventType> stateMachine = this.factory.getStateMachine(UUID.randomUUID());
    stateMachine.start();

    stateMachine.sendEvent(EmployeeEventType.EMPLOYEE_CREATED);
    assertEquals(EmployeeState.ADDED, stateMachine.getState().getId());
    System.out.println("after EMPLOYEE_CREATED " + stateMachine.getState().toString());

    stateMachine.sendEvent(EmployeeEventType.READY_TO_CHECK);
    assertEquals(EmployeeState.IN_CHECK, stateMachine.getState().getId());
    System.out.println("after READY TOCHECK " + stateMachine.getState().toString());

    stateMachine.sendEvent(EmployeeEventType.SECURITY_CHECK_FINISHED);
    assertTrue(stateMachine.getState().getIds().contains(EmployeeState.SECURITY_CHECK_FINISHED));
    assertTrue(stateMachine.getState().getIds().contains(EmployeeState.WORK_PERMIT_CHECK_STARTED));
    System.out.println("after SECURITY_CHECK_FINISHED " + stateMachine.getState().toString());

    stateMachine.sendEvent(EmployeeEventType.WORK_PERMIT_CHECK_FINISHED);
    System.out.println("after sent event WORK_PERMIT_CHECK_FINISHED " + stateMachine.getState().toString());
    assertTrue(stateMachine.getState().getIds().contains(EmployeeState.APPROVED));
   // assertTrue(stateMachine.getState().getIds().contains(EmployeeState.WORK_PERMIT_CHECK_FINISHED));
   // assertTrue(stateMachine.getState().getIds().contains(EmployeeState.SECURITY_CHECK_FINISHED));
    System.out.println("after WORK_PERMIT_CHECK_FINISHED " + stateMachine.getState().toString());

    stateMachine.sendEvent(EmployeeEventType.ACTIVATED);
    System.out.println("after sent  APPROVED evet " + stateMachine.getState().toString());
    assertTrue(stateMachine.getState().getIds().contains(EmployeeState.ACTIVE));
    System.out.println(stateMachine.getState().toString());

    stateMachine.stop();
  }
}
