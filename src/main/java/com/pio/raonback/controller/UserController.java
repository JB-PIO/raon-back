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
  public ResponseEntity<ResponseDto> updateNickname(@RequestBody @Valid UpdateNicknameRequestDto requestBody,
                                                    @AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<ResponseDto> response = userService.updateNickname(requestBody, principal);
    return response;
  }

  @PatchMapping("/profile-image")
  public ResponseEntity<ResponseDto> updateProfileImage(@RequestBody @Valid UpdateProfileImageRequestDto requestBody,
                                                        @AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<ResponseDto> response = userService.updateProfileImage(requestBody, principal);
    return response;
  }

  @PatchMapping("/location")
  public ResponseEntity<ResponseDto> updateLocation(@RequestBody @Valid UpdateLocationRequestDto requestBody,
                                                    @AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<ResponseDto> response = userService.updateLocation(requestBody, principal);
    return response;
  }

}
