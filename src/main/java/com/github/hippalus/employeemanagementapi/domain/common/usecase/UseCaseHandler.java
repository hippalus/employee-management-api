package com.github.hippalus.employeemanagementapi.domain.common.usecase;


import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;

public interface UseCaseHandler<R, T extends UseCase> {

  R handle(T useCase);
}
