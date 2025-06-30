package com.pio.raonback.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("jwt")
public class JwtProperties {

  private final String secret;
  private final Long accessTokenExpirationTime;
  private final Long refreshTokenExpirationTime;

}
