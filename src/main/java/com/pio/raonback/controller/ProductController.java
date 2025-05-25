package com.pio.raonback.controller;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.response.product.GetProductListResponseDto;
import com.pio.raonback.dto.response.product.PostProductResponseDto;
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

  @PostMapping("")
  public ResponseEntity<? super PostProductResponseDto> postProduct(@RequestBody @Valid PostProductRequestDto requestBody,
                                                                    @AuthenticationPrincipal String email) {
    ResponseEntity<? super PostProductResponseDto> response = productService.postProduct(requestBody, email);
    return response;
  }

}
