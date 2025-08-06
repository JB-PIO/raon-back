package com.pio.raonback.service;

import com.pio.raonback.dto.response.user.GetProductListResponseDto;
import com.pio.raonback.dto.response.user.GetProfileResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<? super GetProfileResponseDto> getProfile(Long userId);

  ResponseEntity<? super GetProductListResponseDto> getProductList(Long userId, Pageable pageable);

}
