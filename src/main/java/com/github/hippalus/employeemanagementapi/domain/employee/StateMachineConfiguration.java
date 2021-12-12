package com.github.hippalus.employeemanagementapi.domain.employee;

import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEventType> {

  @SneakyThrows
  @Override
  public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEventType> config) {
    config
        .withConfiguration()
        .listener(listener());
  }

  @SneakyThrows
  @Override
  public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEventType> states) {
    states.withStates()
        .initial(EmployeeState.ADDED)
        .state(EmployeeState.IN_CHECK)
        .state(EmployeeState.APPROVED)
        .end(EmployeeState.ACTIVE);

  }

  @SneakyThrows
  @Override
  public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEventType> transitions) {
    transitions
        .withExternal()
        .source(EmployeeState.ADDED).target(EmployeeState.IN_CHECK).event(EmployeeEventType.READY_TO_CHECK)
        .and()
        .withExternal()
        .source(EmployeeState.IN_CHECK).target(EmployeeState.APPROVED).event(EmployeeEventType.APPROVED)
        .and()
        .withExternal()
        .source(EmployeeState.APPROVED).target(EmployeeState.IN_CHECK).event(EmployeeEventType.RECHECK)
        .and()
        .withExternal()
        .source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE).event(EmployeeEventType.ACTIVATED);
  }

  @Bean
  public StateMachineListener<EmployeeState, EmployeeEventType> listener() {
    return new StateMachineListenerAdapter<>() {
      @Override
      public void stateChanged(State<EmployeeState, EmployeeEventType> from, State<EmployeeState, EmployeeEventType> to) {
        log.info("State changed from {} to {} ", from, to);
      }
    };
  }
}
