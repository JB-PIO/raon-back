package com.pio.raonback.controller;

import com.pio.raonback.dto.request.auth.SignInRequestDto;
import com.pio.raonback.dto.request.auth.SignUpRequestDto;
import com.pio.raonback.dto.response.auth.RefreshTokenResponseDto;
import com.pio.raonback.dto.response.auth.SignInResponseDto;
import com.pio.raonback.dto.response.auth.SignOutResponseDto;
import com.pio.raonback.dto.response.auth.SignUpResponseDto;
import com.pio.raonback.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<? super RefreshTokenResponseDto> refreshToken(@CookieValue(value = "refreshToken") String refreshToken) {
    ResponseEntity<? super RefreshTokenResponseDto> response = authService.refreshToken(refreshToken);
    return response;
  }

  @PostMapping("/sign-out")
  public ResponseEntity<? super SignOutResponseDto> signOut(@CookieValue(value = "refreshToken") String refreshToken) {
    ResponseEntity<? super SignOutResponseDto> response = authService.signOut(refreshToken);
    return response;
  }

}
