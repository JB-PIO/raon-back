package com.pio.raonback.security;

import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaonUserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByIsDeletedFalseAndEmail(email)
                                          .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    return new RaonUser(userEntity);
  }

}
