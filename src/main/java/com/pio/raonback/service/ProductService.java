package com.pio.raonback.service;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.UpdateProductRequestDto;
import com.pio.raonback.dto.response.product.*;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  ResponseEntity<? super GetProductListResponseDto> getProductList(int page, int size);

  ResponseEntity<? super GetNearbyProductListResponseDto> getNearbyProductList(Long locationId, int page, int size);

  ResponseEntity<? super GetProductResponseDto> getProduct(Long productId);

  ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, String email);

  ResponseEntity<? super UpdateProductResponseDto> updateProduct(Long productId, UpdateProductRequestDto dto, String email);

  ResponseEntity<? super DeleteProductResponseDto> deleteProduct(Long productId, String email);

}
