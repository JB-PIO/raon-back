package com.pio.raonback.service;

import com.pio.raonback.dto.request.me.UpdateLocationRequestDto;
import com.pio.raonback.dto.request.me.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.me.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.me.GetFavoritesResponseDto;
import com.pio.raonback.dto.response.me.GetProductsResponseDto;
import com.pio.raonback.dto.response.me.GetProfileResponseDto;
import com.pio.raonback.dto.response.me.GetTradesResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MeService {

  ResponseEntity<? super GetProfileResponseDto> getProfile(RaonUser principal);

  ResponseEntity<? super GetProductsResponseDto> getProducts(Pageable pageable, RaonUser principal);

  ResponseEntity<? super GetFavoritesResponseDto> getFavorites(Pageable pageable, RaonUser principal);

  ResponseEntity<? super GetTradesResponseDto> getTrades(String type, Pageable pageable, RaonUser principal);

  ResponseEntity<ResponseDto> updateNickname(UpdateNicknameRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> updateProfileImage(UpdateProfileImageRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> updateLocation(UpdateLocationRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> deleteAccount(RaonUser principal);

  ResponseEntity<ResponseDto> deleteProfileImage(RaonUser principal);

}
