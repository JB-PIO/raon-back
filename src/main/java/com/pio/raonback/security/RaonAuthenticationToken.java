package com.pio.raonback.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class RaonAuthenticationToken extends AbstractAuthenticationToken {

  private final RaonUser principal;

  public RaonAuthenticationToken(RaonUser principal) {
    super(principal.getAuthorities());
    this.principal = principal;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

}
