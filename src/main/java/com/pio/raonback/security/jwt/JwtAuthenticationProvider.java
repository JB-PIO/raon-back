package com.pio.raonback.security.jwt;

import com.pio.raonback.security.JwtAuthenticationToken;
import com.pio.raonback.security.RaonAuthenticationToken;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.security.exception.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = ((JwtAuthenticationToken) authentication).getToken();
    if (token == null || token.isEmpty()) throw new BadCredentialsException("토큰이 없습니다.");
    try {
      String email = jwtUtil.validateAccessToken(token);
      RaonUser principal = (RaonUser) userDetailsService.loadUserByUsername(email);
      if (principal.getUser().getIsSuspended()) throw new LockedException("정지된 계정입니다.");
      return new RaonAuthenticationToken(principal);
    } catch (io.jsonwebtoken.ExpiredJwtException exception) {
      if (exception.getClaims().get("type", String.class).equals("access")) {
        throw new ExpiredJwtException("만료된 토큰입니다.");
      } else {
        throw new BadCredentialsException("유효하지 않은 토큰입니다.");
      }
    } catch (JwtException exception) {
      System.out.println(exception.getMessage());
      throw new BadCredentialsException("유효하지 않은 토큰입니다.");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
