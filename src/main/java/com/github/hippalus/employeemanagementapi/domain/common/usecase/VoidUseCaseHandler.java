package com.github.hippalus.employeemanagementapi.domain.common.usecase;


import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;

public interface VoidUseCaseHandler<T extends UseCase> {

  void handle(T useCase);
}
