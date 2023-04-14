package com.example.facebookapiserver.security;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ClassUtils.isAssignable;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import com.example.facebook.user.UserService;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ConnectionBasedVoter implements AccessDecisionVoter<FilterInvocation> {

  private final RequestMatcher requiresAuthorizationRequestMatcher;

  private final Function<String, Id<User, Long>> idExtractor;

  private UserService userService;

  public ConnectionBasedVoter(RequestMatcher requiresAuthorizationRequestMatcher, Function<String, Id<User, Long>> idExtractor) {
    checkArgument(requiresAuthorizationRequestMatcher != null, "requiresAuthorizationRequestMatcher must be provided.");
    checkArgument(idExtractor != null, "idExtractor must be provided.");

    this.requiresAuthorizationRequestMatcher = requiresAuthorizationRequestMatcher;
    this.idExtractor = idExtractor;
  }

  @Override
  public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
    HttpServletRequest request = fi.getRequest();

    if (!requiresAuthorization(request)) {
      return ACCESS_GRANTED;
    }

    if (!isAssignable(JwtAuthenticationToken.class, authentication.getClass())) {
      return ACCESS_ABSTAIN;
    }

    JwtAuthentication jwtAuth = (JwtAuthentication) authentication.getPrincipal();
    Id<User, Long> targetId = obtainTargetId(request);

    // 본인 자신
    if (jwtAuth.id.equals(targetId)) {
      return ACCESS_GRANTED;
    }

    // 친구IDs 조회 후 targetId가 포함되는지 확인한다.
    List<Id<User, Long>> connectedIds = userService.findConnectedIds(jwtAuth.id);
    if (connectedIds.contains(targetId)) {
      return ACCESS_GRANTED;
    }

    return ACCESS_DENIED;
  }

  private boolean requiresAuthorization(HttpServletRequest request) {
    return requiresAuthorizationRequestMatcher.matches(request);
  }

  private Id<User, Long> obtainTargetId(HttpServletRequest request) {
    return idExtractor.apply(request.getRequestURI());
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return isAssignable(FilterInvocation.class, clazz);
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

}