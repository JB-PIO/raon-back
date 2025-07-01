package com.pio.raonback.service;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.UpdateProductRequestDto;
import com.pio.raonback.dto.response.product.*;
import com.pio.raonback.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  ResponseEntity<? super GetProductListResponseDto> getProductList(int page, int size);

  ResponseEntity<? super GetNearbyProductListResponseDto> getNearbyProductList(Long locationId, int page, int size);

  ResponseEntity<? super GetProductResponseDto> getProduct(Long productId);

  ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, CustomUserDetails userDetails);

  ResponseEntity<? super UpdateProductResponseDto> updateProduct(Long productId, UpdateProductRequestDto dto, CustomUserDetails userDetails);

  ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Long productId);

  ResponseEntity<? super DeleteProductResponseDto> deleteProduct(Long productId, CustomUserDetails userDetails);

}
