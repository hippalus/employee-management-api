package com.github.hippalus.employeemanagementapi.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ValidatorConfig implements WebMvcConfigurer {

  private final Validator defaultValidator;

  @Override
  public Validator getValidator() {
    return defaultValidator;
  }
}
