package com.pio.raonback.service;

import com.pio.raonback.dto.request.auth.SignInRequestDto;
import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.auth.RefreshTokenResponseDto;
import com.pio.raonback.dto.response.auth.SignInResponseDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

  ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

  ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

  ResponseEntity<? super RefreshTokenResponseDto> refreshToken(String refreshToken);

  ResponseEntity<ResponseDto> signOut(String refreshToken);

}
