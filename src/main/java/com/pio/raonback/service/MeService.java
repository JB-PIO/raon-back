package com.pio.raonback.service;

import com.pio.raonback.dto.request.me.UpdateProfileRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.me.GetFavoriteListResponseDto;
import com.pio.raonback.dto.response.me.GetProductListResponseDto;
import com.pio.raonback.dto.response.me.GetProfileResponseDto;
import com.pio.raonback.dto.response.me.GetTradeListResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MeService {

  ResponseEntity<? super GetProfileResponseDto> getProfile(RaonUser principal);

  ResponseEntity<? super GetProductListResponseDto> getProductList(Pageable pageable, RaonUser principal);

  ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Pageable pageable, RaonUser principal);

  ResponseEntity<? super GetTradeListResponseDto> getTradeList(String type, Pageable pageable, RaonUser principal);

  ResponseEntity<ResponseDto> updateProfile(UpdateProfileRequestDto dto, RaonUser principal);

}
