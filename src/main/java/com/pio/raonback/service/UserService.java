package com.pio.raonback.service;

import com.pio.raonback.dto.response.user.GetProductListResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<? super GetProductListResponseDto> getProductList(Long userId, Pageable pageable);

}
