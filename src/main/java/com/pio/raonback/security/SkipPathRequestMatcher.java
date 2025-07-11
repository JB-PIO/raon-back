package com.pio.raonback.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher {

  private OrRequestMatcher matchersToSkip;
  private RequestMatcher processingMatcher;

  public SkipPathRequestMatcher(OrRequestMatcher matchersToSkip, RequestMatcher processingMatcher) {
    this.matchersToSkip = matchersToSkip;
    this.processingMatcher = processingMatcher;
  }

  @Override
  public boolean matches(HttpServletRequest request) {
    if (matchersToSkip.matches(request)) return false;
    return processingMatcher.matches(request);
  }

}
