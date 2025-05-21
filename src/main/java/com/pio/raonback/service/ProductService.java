package com.pio.raonback.service;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.response.product.PostProductResponseDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, String email);

}
