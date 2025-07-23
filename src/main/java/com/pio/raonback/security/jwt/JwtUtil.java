package com.pio.raonback.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  private final JwtProperties jwtProperties;

  public String generateAccessToken(String email) {
    Date expiryDate = Date.from(Instant.now().plus(jwtProperties.getAccessTokenExpirationTime(), ChronoUnit.SECONDS));
    SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    return Jwts.builder()
               .subject(email)
               .claim("type", "access")
               .issuedAt(new Date())
               .expiration(expiryDate)
               .signWith(key, Jwts.SIG.HS256)
               .compact();
  }

  public String generateRefreshToken(String email) {
    Date expiryDate = Date.from(Instant.now().plus(jwtProperties.getRefreshTokenExpirationTime(), ChronoUnit.SECONDS));
    SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    return Jwts.builder()
               .subject(email)
               .claim("type", "refresh")
               .issuedAt(new Date())
               .expiration(expiryDate)
               .signWith(key, Jwts.SIG.HS256)
               .compact();
  }

  public String validateAccessToken(String accessToken) throws JwtException {
    SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    return Jwts.parser()
               .require("type", "access")
               .verifyWith(key)
               .build()
               .parseSignedClaims(accessToken)
               .getPayload()
               .getSubject();
  }

  public String validateRefreshToken(String refreshToken) throws JwtException {
    SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    return Jwts.parser()
               .require("type", "refresh")
               .verifyWith(key)
               .build()
               .parseSignedClaims(refreshToken)
               .getPayload()
               .getSubject();
  }

  public String generateTokenHash(String token) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-512");
      byte[] hashBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder();
      for (byte b : hashBytes) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException exception) {
      throw new RuntimeException("SHA-512 algorithm not available.", exception);
    }
  }

  public LocalDateTime getExpirationFromToken(String token) {
    SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    return Jwts.parser()
               .verifyWith(key)
               .build()
               .parseSignedClaims(token)
               .getPayload()
               .getExpiration()
               .toInstant()
               .atZone(ZoneId.systemDefault())
               .toLocalDateTime();
  }

}
