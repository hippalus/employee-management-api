package com.github.hippalus.employeemanagementapi.domain.common.usecase;


import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;

public interface UseCasePublisher {

  <R, T extends UseCase> R publish(Class<R> returnClass, T useCase);

  <R, T extends UseCase> void publish(T useCase);
}
