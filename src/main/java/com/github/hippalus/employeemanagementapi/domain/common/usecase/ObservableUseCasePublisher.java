package com.github.hippalus.employeemanagementapi.domain.common.usecase;


import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;

public class ObservableUseCasePublisher extends BeanAwareUseCasePublisher {

  public <R, T extends UseCase> void register(Class<T> useCaseClass, UseCaseHandler<R, T> useCaseHandler) {
    UseCaseHandlerRegistry.INSTANCE.register(useCaseClass, useCaseHandler);
  }

  public <T extends UseCase> void register(Class<T> useCaseClass, VoidUseCaseHandler<T> useCaseHandler) {
    UseCaseHandlerRegistry.INSTANCE.register(useCaseClass, useCaseHandler);
  }
}
