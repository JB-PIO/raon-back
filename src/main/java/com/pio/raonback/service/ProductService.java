package com.pio.raonback.service;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.PutFavoriteRequestDto;
import com.pio.raonback.dto.request.product.UpdateProductRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.product.*;
import com.pio.raonback.security.RaonUser;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  ResponseEntity<? super GetProductListResponseDto> getProductList(int page, int size);

  ResponseEntity<? super GetNearbyProductListResponseDto> getNearbyProductList(Long locationId, int page, int size);

  ResponseEntity<? super GetProductResponseDto> getProduct(Long productId);

  ResponseEntity<? super GetChatResponseDto> getChat(Long productId, RaonUser principal);

  ResponseEntity<? super PostProductResponseDto> postProduct(PostProductRequestDto dto, RaonUser principal);

  ResponseEntity<? super CreateChatResponseDto> createChat(Long productId, RaonUser principal);

  ResponseEntity<? super UpdateProductResponseDto> updateProduct(Long productId, UpdateProductRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> putFavorite(Long productId, PutFavoriteRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> increaseViewCount(Long productId);

  ResponseEntity<ResponseDto> deleteProduct(Long productId, RaonUser principal);

}
