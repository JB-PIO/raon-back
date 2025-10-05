package com.pio.raonback.controller;

import com.pio.raonback.dto.request.product.*;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.product.*;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("")
  public ResponseEntity<? super GetProductListResponseDto> getProducts(
      @ModelAttribute @Valid GetProductListRequestDto requestParam,
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "status", direction = Sort.Direction.ASC),
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {
    return productService.getProducts(requestParam, pageable);
  }

  @GetMapping("/{productId}")
  public ResponseEntity<? super GetProductResponseDto> getProduct(@PathVariable("productId") Long productId) {
    return productService.getProduct(productId);
  }

  @GetMapping("/{productId}/chat")
  public ResponseEntity<? super GetChatResponseDto> getChat(@PathVariable("productId") Long productId,
                                                            @AuthenticationPrincipal RaonUser principal) {
    return productService.getChat(productId, principal);
  }

  @GetMapping("/{productId}/buyer")
  public ResponseEntity<? super GetBuyerListResponseDto> getBuyers(
      @PathVariable("productId") Long productId,
      @PageableDefault(size = 10, sort = "lastMessageAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal RaonUser principal
  ) {
    return productService.getBuyers(productId, pageable, principal);
  }

  @PostMapping("")
  public ResponseEntity<? super PostProductResponseDto> postProduct(@RequestBody @Valid PostProductRequestDto requestBody,
                                                                    @AuthenticationPrincipal RaonUser principal) {
    return productService.postProduct(requestBody, principal);
  }

  @PostMapping("/{productId}/chat")
  public ResponseEntity<? super CreateChatResponseDto> createChat(@PathVariable("productId") Long productId,
                                                                  @AuthenticationPrincipal RaonUser principal) {
    return productService.createChat(productId, principal);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<? super UpdateProductResponseDto> updateProduct(@PathVariable("productId") Long productId,
                                                                        @RequestBody @Valid UpdateProductRequestDto requestBody,
                                                                        @AuthenticationPrincipal RaonUser principal) {
    return productService.updateProduct(productId, requestBody, principal);
  }

  @PutMapping("/{productId}/favorite")
  public ResponseEntity<ResponseDto> putFavorite(@PathVariable("productId") Long productId,
                                                 @RequestBody @Valid PutFavoriteRequestDto requestBody,
                                                 @AuthenticationPrincipal RaonUser principal) {
    return productService.putFavorite(productId, requestBody, principal);
  }

  @PatchMapping("/{productId}/status")
  public ResponseEntity<ResponseDto> updateStatus(@PathVariable("productId") Long productId,
                                                  @RequestBody @Valid UpdateStatusRequestDto requestBody,
                                                  @AuthenticationPrincipal RaonUser principal) {
    return productService.updateStatus(productId, requestBody, principal);
  }

  @PatchMapping("/{productId}/view")
  public ResponseEntity<ResponseDto> increaseViewCount(@PathVariable("productId") Long productId) {
    return productService.increaseViewCount(productId);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<ResponseDto> deleteProduct(@PathVariable("productId") Long productId,
                                                   @AuthenticationPrincipal RaonUser principal) {
    return productService.deleteProduct(productId, principal);
  }

}
