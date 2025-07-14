package com.pio.raonback.controller;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.UpdateProductRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.product.CreateChatResponseDto;
import com.pio.raonback.dto.response.product.GetNearbyProductListResponseDto;
import com.pio.raonback.dto.response.product.GetProductListResponseDto;
import com.pio.raonback.dto.response.product.GetProductResponseDto;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("")
  public ResponseEntity<? super GetProductListResponseDto> getProductList(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "30") int size) {
    ResponseEntity<? super GetProductListResponseDto> response = productService.getProductList(page, size);
    return response;
  }

  @GetMapping("/nearby/{locationId}")
  public ResponseEntity<? super GetNearbyProductListResponseDto> getNearbyProductList(@PathVariable("locationId") Long locationId,
                                                                                      @RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "30") int size) {
    ResponseEntity<? super GetNearbyProductListResponseDto> response = productService.getNearbyProductList(locationId, page, size);
    return response;
  }

  @GetMapping("/{productId}")
  public ResponseEntity<? super GetProductResponseDto> getProduct(@PathVariable("productId") Long productId) {
    ResponseEntity<? super GetProductResponseDto> response = productService.getProduct(productId);
    return response;
  }

  @PostMapping("")
  public ResponseEntity<ResponseDto> postProduct(@RequestBody @Valid PostProductRequestDto requestBody,
                                                 @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<ResponseDto> response = productService.postProduct(requestBody, user);
    return response;
  }

  @PostMapping("/{productId}/chat")
  public ResponseEntity<? super CreateChatResponseDto> createChat(@PathVariable("productId") Long productId,
                                                                  @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<? super CreateChatResponseDto> response = productService.createChat(productId, user);
    return response;
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ResponseDto> updateProduct(@PathVariable("productId") Long productId,
                                                   @RequestBody @Valid UpdateProductRequestDto requestBody,
                                                   @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<ResponseDto> response = productService.updateProduct(productId, requestBody, user);
    return response;
  }

  @PatchMapping("/{productId}/view")
  public ResponseEntity<ResponseDto> increaseViewCount(@PathVariable("productId") Long productId) {
    ResponseEntity<ResponseDto> response = productService.increaseViewCount(productId);
    return response;
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ResponseDto> deleteProduct(@PathVariable("productId") Long productId,
                                                   @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<ResponseDto> response = productService.deleteProduct(productId, user);
    return response;
  }

}
