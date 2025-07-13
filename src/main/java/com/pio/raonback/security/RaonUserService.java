package com.pio.raonback.security;

import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.repository.UserRepository;
import com.pio.raonback.security.exception.UsernameCannotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RaonUserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserEntity> optionalUserEntity;
    try {
      optionalUserEntity = userRepository.findByIsDeletedFalseAndEmail(email);
    } catch (Exception exception) {
      throw new UsernameCannotFoundException(exception.getMessage());
    }
    if (optionalUserEntity.isEmpty()) throw new UsernameNotFoundException("User not found with email: " + email);
    UserEntity userEntity = optionalUserEntity.get();
    return new RaonUser(userEntity);
  }

}
