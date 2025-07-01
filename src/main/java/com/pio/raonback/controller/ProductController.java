package com.pio.raonback.controller;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.UpdateProductRequestDto;
import com.pio.raonback.dto.response.product.*;
import com.pio.raonback.security.CustomUserDetails;
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
  public ResponseEntity<? super PostProductResponseDto> postProduct(@RequestBody @Valid PostProductRequestDto requestBody,
                                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
    ResponseEntity<? super PostProductResponseDto> response = productService.postProduct(requestBody, userDetails);
    return response;
  }

  @PutMapping("/{productId}")
  public ResponseEntity<? super UpdateProductResponseDto> updateProduct(@PathVariable("productId") Long productId,
                                                                        @RequestBody @Valid UpdateProductRequestDto requestBody,
                                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
    ResponseEntity<? super UpdateProductResponseDto> response = productService.updateProduct(productId, requestBody, userDetails);
    return response;
  }

  @PatchMapping("/{productId}/view")
  public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(@PathVariable("productId") Long productId) {
    ResponseEntity<? super IncreaseViewCountResponseDto> response = productService.increaseViewCount(productId);
    return response;
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<? super DeleteProductResponseDto> deleteProduct(@PathVariable("productId") Long productId,
                                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
    ResponseEntity<? super DeleteProductResponseDto> response = productService.deleteProduct(productId, userDetails);
    return response;
  }

}
