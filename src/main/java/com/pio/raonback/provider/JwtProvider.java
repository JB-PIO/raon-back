package com.pio.raonback.provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

  @Value("${spring.jwt.secret}")
  private String secret;

  public String create(String email) {
    Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
    SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    return Jwts.builder()
               .subject(email)
               .expiration(expiredDate)
               .signWith(key, Jwts.SIG.HS256)
               .compact();
  }

  public String validate(String jws) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
      return Jwts.parser()
                 .verifyWith(key)
                 .build().parseSignedClaims(jws).getPayload().getSubject();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
  }

}
