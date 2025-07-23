package com.pio.raonback.security;

import com.pio.raonback.entity.User;
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
    Optional<User> optionalUser;
    try {
      optionalUser = userRepository.findByIsDeletedFalseAndEmail(email);
    } catch (Exception exception) {
      throw new UsernameCannotFoundException(exception.getMessage());
    }
    if (optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found with email: " + email);
    User user = optionalUser.get();
    return new RaonUser(user);
  }

}
