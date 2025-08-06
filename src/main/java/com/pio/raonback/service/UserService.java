package com.pio.raonback.service;

import com.pio.raonback.dto.request.user.UpdateProfileRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.user.GetFavoriteListResponseDto;
import com.pio.raonback.dto.response.user.GetProductListResponseDto;
import com.pio.raonback.dto.response.user.GetProfileResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<? super GetProfileResponseDto> getProfile(RaonUser principal);

  ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Pageable pageable, RaonUser principal);

  ResponseEntity<? super GetProductListResponseDto> getProductList(Long userId, Pageable pageable);

  ResponseEntity<ResponseDto> updateProfile(UpdateProfileRequestDto dto, RaonUser principal);

}
