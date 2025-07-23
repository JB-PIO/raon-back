package com.pio.raonback.service;

import com.pio.raonback.dto.request.user.UpdateLocationRequestDto;
import com.pio.raonback.dto.request.user.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.user.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<ResponseDto> updateNickname(UpdateNicknameRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> updateProfileImage(UpdateProfileImageRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> updateLocation(UpdateLocationRequestDto dto, RaonUser principal);

}
