package com.pio.raonback.controller;

import com.pio.raonback.dto.request.product.PostProductRequestDto;
import com.pio.raonback.dto.response.product.PostProductResponseDto;
import com.pio.raonback.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("")
  public ResponseEntity<? super PostProductResponseDto> postProduct(@RequestBody @Valid PostProductRequestDto requestBody,
                                                                    @AuthenticationPrincipal String email) {
    ResponseEntity<? super PostProductResponseDto> response = productService.postProduct(requestBody, email);
    return response;
  }

}
