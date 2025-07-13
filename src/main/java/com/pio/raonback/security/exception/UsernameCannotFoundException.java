package com.pio.raonback.security.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsernameCannotFoundException extends UsernameNotFoundException {

  public UsernameCannotFoundException(String msg) {
    super(msg);
  }

  public UsernameCannotFoundException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
