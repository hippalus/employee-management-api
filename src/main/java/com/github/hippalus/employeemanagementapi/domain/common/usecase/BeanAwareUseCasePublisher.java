package com.github.hippalus.employeemanagementapi.domain.common.usecase;

import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BeanAwareUseCasePublisher implements UseCasePublisher {

  @Override
  @SuppressWarnings("unchecked")
  public <R, T extends UseCase> R publish(Class<R> returnClass, T useCase) {
    var useCaseHandler = (UseCaseHandler<R, T>) UseCaseHandlerRegistry.INSTANCE.detectUseCaseHandlerFrom(useCase.getClass());
    return useCaseHandler.handle(useCase);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <R, T extends UseCase> void publish(T useCase) {
    var voidUseCaseHandler = (VoidUseCaseHandler<T>) UseCaseHandlerRegistry.INSTANCE.detectVoidUseCaseHandlerFrom(
        useCase.getClass());
    if (Objects.isNull(voidUseCaseHandler)) {
      var useCaseHandler = (UseCaseHandler<R, T>) UseCaseHandlerRegistry.INSTANCE.detectUseCaseHandlerFrom(useCase.getClass());
      useCaseHandler.handle(useCase);
    } else {
      voidUseCaseHandler.handle(useCase);
    }
  }
}

