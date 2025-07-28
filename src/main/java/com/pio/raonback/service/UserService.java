package com.pio.raonback.service;

import com.pio.raonback.dto.request.user.UpdateProfileRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<ResponseDto> updateProfile(UpdateProfileRequestDto dto, RaonUser principal);

}
