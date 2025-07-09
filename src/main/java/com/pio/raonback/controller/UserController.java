package com.pio.raonback.controller;

import com.pio.raonback.dto.request.user.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.user.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.user.UpdateNicknameResponseDto;
import com.pio.raonback.dto.response.user.UpdateProfileImageResponseDto;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PatchMapping("/nickname")
  public ResponseEntity<? super UpdateNicknameResponseDto> updateNickname(@RequestBody @Valid UpdateNicknameRequestDto requestBody,
                                                                          @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<? super UpdateNicknameResponseDto> response = userService.updateNickname(requestBody, user);
    return response;
  }

  @PatchMapping("/profile-image")
  public ResponseEntity<? super UpdateProfileImageResponseDto> updateProfileImage(@RequestBody @Valid UpdateProfileImageRequestDto requestBody,
                                                                                  @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<? super UpdateProfileImageResponseDto> response = userService.updateProfileImage(requestBody, user);
    return response;
  }

}
