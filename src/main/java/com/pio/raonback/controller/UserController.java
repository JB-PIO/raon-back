package com.pio.raonback.controller;

import com.pio.raonback.dto.request.user.UpdateProfileRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.user.GetProfileResponseDto;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<? super GetProfileResponseDto> getProfile(@AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<? super GetProfileResponseDto> response = userService.getProfile(principal);
    return response;
  }

  @PatchMapping("/me")
  public ResponseEntity<ResponseDto> updateProfile(@RequestBody @Valid UpdateProfileRequestDto requestBody,
                                                   @AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<ResponseDto> response = userService.updateProfile(requestBody, principal);
    return response;
  }

}
