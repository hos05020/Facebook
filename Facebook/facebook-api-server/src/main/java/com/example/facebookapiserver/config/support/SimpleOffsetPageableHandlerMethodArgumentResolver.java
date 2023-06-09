package com.example.facebookapiserver.config.support;

import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static org.apache.commons.lang3.math.NumberUtils.toLong;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SimpleOffsetPageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private static final int DEFAULT_OFFSET = 0;

  private static final int DEFAULT_LIMIT = 5;

  private final String offsetParam;

  private final String limitParam;

  public SimpleOffsetPageableHandlerMethodArgumentResolver() {
    this("offset", "limit");
  }

  public SimpleOffsetPageableHandlerMethodArgumentResolver(String offsetParam, String limitParam) {
    this.offsetParam = offsetParam;
    this.limitParam = limitParam;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Pageable.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    String offsetString = webRequest.getParameter(offsetParam);
    String limitString = webRequest.getParameter(limitParam);

    long offset = toLong(offsetString, DEFAULT_OFFSET);
    int limit = toInt(limitString, DEFAULT_LIMIT);

    if (offset < 0) {
      offset = DEFAULT_OFFSET;
    }
    if (limit < 1 || limit > 5) {
      limit = DEFAULT_LIMIT;
    }

    return new SimpleOffsetPageRequest(offset, limit);
  }

}