package com.pio.raonback.security;

import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
    if (optionalUserEntity.isEmpty()) throw new UsernameNotFoundException("User not found with email: " + email);
    UserEntity userEntity = optionalUserEntity.get();
    if (userEntity.getIsDeleted() || userEntity.getIsSuspended())
      throw new UsernameNotFoundException("User account is not active.");
    return new CustomUserDetails(userEntity);
  }

}
