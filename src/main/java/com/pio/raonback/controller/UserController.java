package com.pio.raonback.controller;

import com.pio.raonback.dto.request.user.UpdateLocationRequestDto;
import com.pio.raonback.dto.request.user.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.user.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
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
  public ResponseEntity<? super ResponseDto> updateNickname(@RequestBody @Valid UpdateNicknameRequestDto requestBody,
                                                            @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<? super ResponseDto> response = userService.updateNickname(requestBody, user);
    return response;
  }

  @PatchMapping("/profile-image")
  public ResponseEntity<? super ResponseDto> updateProfileImage(@RequestBody @Valid UpdateProfileImageRequestDto requestBody,
                                                                @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<? super ResponseDto> response = userService.updateProfileImage(requestBody, user);
    return response;
  }

  @PatchMapping("/location")
  public ResponseEntity<? super ResponseDto> updateLocation(@RequestBody @Valid UpdateLocationRequestDto requestBody,
                                                            @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<? super ResponseDto> response = userService.updateLocation(requestBody, user);
    return response;
  }

}
