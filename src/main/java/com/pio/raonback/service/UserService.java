package com.pio.raonback.service;

import com.pio.raonback.dto.request.user.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.user.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.user.UpdateNicknameResponseDto;
import com.pio.raonback.dto.response.user.UpdateProfileImageResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<? super UpdateNicknameResponseDto> updateNickname(UpdateNicknameRequestDto dto, String email);

  ResponseEntity<? super UpdateProfileImageResponseDto> updateProfileImage(UpdateProfileImageRequestDto dto, String email);

}
