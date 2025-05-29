package com.pio.raonback.service;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.PutProductRequestDto;
import com.pio.raonback.dto.response.product.DeleteProductResponseDto;
import com.pio.raonback.dto.response.product.GetProductListResponseDto;
import com.pio.raonback.dto.response.product.PostProductResponseDto;
import com.pio.raonback.dto.response.product.PutProductResponseDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  ResponseEntity<? super GetProductListResponseDto> getProductList(int page, int size);

  ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, String email);

  ResponseEntity<? super PutProductResponseDto> updateProduct(Long productId, PutProductRequestDto dto, String email);

  ResponseEntity<? super DeleteProductResponseDto> deleteProduct(Long productId, String email);

}
