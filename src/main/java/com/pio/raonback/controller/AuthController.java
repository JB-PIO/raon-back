package com.pio.raonback.controller;

import com.pio.raonback.dto.request.auth.RefreshTokenRequestDto;
import com.pio.raonback.dto.request.auth.SignInRequestDto;
import com.pio.raonback.dto.request.auth.SignOutRequestDto;
import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.auth.RefreshTokenResponseDto;
import com.pio.raonback.dto.response.auth.SignInResponseDto;
import com.pio.raonback.dto.response.auth.SignOutResponseDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import com.pio.raonback.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/sign-up")
  public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
    ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
    return response;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
    ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
    return response;
  }

  @PostMapping("/refresh")
  public ResponseEntity<? super RefreshTokenResponseDto> refreshToken(@RequestBody @Valid RefreshTokenRequestDto requestBody) {
    ResponseEntity<? super RefreshTokenResponseDto> response = authService.refreshToken(requestBody);
    return response;
  }

  @PostMapping("/sign-out")
  public ResponseEntity<? super SignOutResponseDto> signOut(@RequestBody @Valid SignOutRequestDto requestBody) {
    ResponseEntity<? super SignOutResponseDto> response = authService.signOut(requestBody);
    return response;
  }

}
