package com.pio.raonback.service;

import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

  ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

}
