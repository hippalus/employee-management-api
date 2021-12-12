package com.github.hippalus.employeemanagementapi.domain.common.usecase;

import com.github.hippalus.employeemanagementapi.domain.common.model.UseCase;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public class UseCaseHandlerRegistry {

  public static final UseCaseHandlerRegistry INSTANCE = Singleton.INSTANCE.registry;

  private final Map<Class<? extends UseCase>, UseCaseHandler<?, ? extends UseCase>> registryForUseCaseHandlers;
  private final Map<Class<? extends UseCase>, VoidUseCaseHandler<? extends UseCase>> registryForVoidUseCaseHandlers;

  private UseCaseHandlerRegistry() {
    registryForUseCaseHandlers = new HashMap<>();
    registryForVoidUseCaseHandlers = new HashMap<>();
  }

  public <R, T extends UseCase> void register(Class<T> key, UseCaseHandler<R, T> useCaseHandler) {
    log.info("Use case {} is registered by handler {}", key.getSimpleName(), useCaseHandler.getClass().getSimpleName());
    registryForUseCaseHandlers.put(key, useCaseHandler);
  }

  public <T extends UseCase> void register(Class<T> key, VoidUseCaseHandler<T> useCaseHandler) {
    log.info("Use case {} is registered by void handler {}", key.getSimpleName(), useCaseHandler.getClass().getSimpleName());
    registryForVoidUseCaseHandlers.put(key, useCaseHandler);
  }

  public UseCaseHandler<?, ? extends UseCase> detectUseCaseHandlerFrom(Class<? extends UseCase> useCaseClass) {
    return registryForUseCaseHandlers.get(useCaseClass);
  }

  public VoidUseCaseHandler<? extends UseCase> detectVoidUseCaseHandlerFrom(Class<? extends UseCase> useCaseClass) {
    return registryForVoidUseCaseHandlers.get(useCaseClass);
  }

  //for thread safety and 100% singleton instance
  private enum Singleton {
    INSTANCE;

    private final UseCaseHandlerRegistry registry;

    Singleton() {
      registry = new UseCaseHandlerRegistry();
    }
  }
}
