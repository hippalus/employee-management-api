package com.github.hippalus.employeemanagementapi.infra.config;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LocalizationConfig extends AcceptHeaderLocaleContextResolver implements WebMvcConfigurer {

  private static final String LANGUAGE_HEADER_NAME = "lang";
  private static final String DEFAULT_LANGUAGE_NAME = "en";
  private static final List<String> SUPPORTED_LANGUAGE = List.of("tr", "en");

  @Bean
  public ResourceBundleMessageSource messageSource() {
    var source = new ResourceBundleMessageSource();
    source.setBasenames("i18n/employee");
    source.setDefaultEncoding("UTF-8");
    return source;
  }

  @NonNull
  @Override
  public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
    List<String> languages = Optional.ofNullable(exchange.getRequest().getHeaders().get(LANGUAGE_HEADER_NAME))
        .orElse(List.of(DEFAULT_LANGUAGE_NAME));

    String language = languages.stream()
        .filter(SUPPORTED_LANGUAGE::contains)
        .findFirst()
        .orElse(DEFAULT_LANGUAGE_NAME);

    return new SimpleLocaleContext(new Locale(language));
  }

  @Override
  public void setLocaleContext(@NonNull ServerWebExchange exchange, LocaleContext localeContext) {
    LocaleContextHolder.setLocale(Objects.requireNonNull(localeContext).getLocale());
  }
}
