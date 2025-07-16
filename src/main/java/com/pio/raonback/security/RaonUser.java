package com.pio.raonback.security;

import com.pio.raonback.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class RaonUser implements UserDetails, Principal {

  private final UserEntity userEntity;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(() -> "ROLE_" + userEntity.getRole().name());
  }

  @Override
  public String getPassword() {
    return userEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return userEntity.getEmail();
  }

  @Override
  public String getName() {
    return userEntity.getUserId().toString();
  }

}
