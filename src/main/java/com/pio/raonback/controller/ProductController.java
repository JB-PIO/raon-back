package com.pio.raonback.controller;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.request.product.PutProductRequestDto;
import com.pio.raonback.dto.response.product.*;
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

  @PostMapping("")
  public ResponseEntity<? super PostProductResponseDto> postProduct(@RequestBody @Valid PostProductRequestDto requestBody,
                                                                    @AuthenticationPrincipal String email) {
    ResponseEntity<? super PostProductResponseDto> response = productService.postProduct(requestBody, email);
    return response;
  }

  @PutMapping("/{productId}")
  public ResponseEntity<? super PutProductResponseDto> updateProduct(@PathVariable("productId") Long productId,
                                                                     @RequestBody @Valid PutProductRequestDto requestBody,
                                                                     @AuthenticationPrincipal String email) {
    ResponseEntity<? super PutProductResponseDto> response = productService.updateProduct(productId, requestBody, email);
    return response;
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<? super DeleteProductResponseDto> deleteProduct(@PathVariable("productId") Long productId,
                                                                        @AuthenticationPrincipal String email) {
    ResponseEntity<? super DeleteProductResponseDto> response = productService.deleteProduct(productId, email);
    return response;
  }

}
