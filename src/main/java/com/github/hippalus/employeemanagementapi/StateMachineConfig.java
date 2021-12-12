//package com.github.hippalus.employeemanagementapi;
//
//import com.github.hippalus.employeemanagementapi.domain.employee.event.EmployeeEventType;
//import com.github.hippalus.employeemanagementapi.domain.employee.model.EmployeeState;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.statemachine.config.EnableStateMachineFactory;
//import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
//import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
//import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
//import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
//import org.springframework.statemachine.listener.StateMachineListener;
//import org.springframework.statemachine.listener.StateMachineListenerAdapter;
//import org.springframework.statemachine.state.State;
//
//@Slf4j
//@Configuration
//@EnableStateMachineFactory
//public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEventType> {
//
//  @SneakyThrows
//  @Override
//  public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEventType> config) {
//    config
//        .withConfiguration()
//        .listener(listener());
//  }
//
//  @SneakyThrows
//  @Override
//  public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEventType> states) {
//    states
//        .withStates()
//        .initial(EmployeeState.ADDED)
//        .fork(EmployeeState.IN_CHECK)
//        .state(EmployeeState.IN_CHECK)
//        .join(EmployeeState.APPROVED)
//        .and()
//        .withStates()
//        .parent(EmployeeState.IN_CHECK)
//        .initial(EmployeeState.SECURITY_CHECK_STARTED)
//        .end(EmployeeState.SECURITY_CHECK_FINISHED)
//        .and()
//        .withStates()
//        .parent(EmployeeState.IN_CHECK)
//        .initial(EmployeeState.WORK_PERMIT_CHECK_STARTED)
//        .end(EmployeeState.WORK_PERMIT_CHECK_FINISHED)
//        .and()
//        .withStates()
//        .state(EmployeeState.APPROVED);
//        //.state(EmployeeState.ACTIVE);*/
//    //.and()
//    //.withStates()
//    // .exit(EmployeeState.ACTIVE);
//
//  }
//
//  @Override
//  public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEventType> transitions) {
//    try {
//      transitions.withExternal()
//          .source(EmployeeState.ADDED)
//          .target(EmployeeState.ADDED)
//          .event(EmployeeEventType.EMPLOYEE_CREATED)
//          .and()
//          .withExternal()
//          .source(EmployeeState.ADDED)
//          .target(EmployeeState.IN_CHECK)
//          .event(EmployeeEventType.READY_TO_CHECK)
//          .and()
//          .withFork()
//          .source(EmployeeState.IN_CHECK)
//          .target(EmployeeState.IN_CHECK)
//          .and()
//          .withExternal()
//          .source(EmployeeState.SECURITY_CHECK_STARTED)
//          .target(EmployeeState.SECURITY_CHECK_FINISHED)
//          .event(EmployeeEventType.SECURITY_CHECK_FINISHED)
//          .and()
//          .withExternal()
//          .source(EmployeeState.WORK_PERMIT_CHECK_STARTED)
//          .target(EmployeeState.WORK_PERMIT_CHECK_FINISHED)
//          .event(EmployeeEventType.WORK_PERMIT_CHECK_FINISHED)
//          .and()
//          .withJoin()
//          .source(EmployeeState.IN_CHECK)
//          .target(EmployeeState.APPROVED)
//          .and()
//          .withExternal()
//          .source(EmployeeState.APPROVED)
//          .target(EmployeeState.ACTIVE)
//          .event(EmployeeEventType.ACTIVATED);
//
//    } catch (Exception e) {
//      throw new RuntimeException("Could not configure state machine transitions", e);
//    }
//  }
//
//  @Bean
//  public StateMachineListener<EmployeeState, EmployeeEventType> listener() {
//    return new StateMachineListenerAdapter<>() {
//      @Override
//      public void stateChanged(State<EmployeeState, EmployeeEventType> from, State<EmployeeState, EmployeeEventType> to) {
//        log.info("State changed from {} to {} ", from, to);
//      }
//    };
//  }
//}
