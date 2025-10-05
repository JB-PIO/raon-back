package com.pio.raonback.controller;

import com.pio.raonback.dto.response.user.GetProductsResponseDto;
import com.pio.raonback.dto.response.user.GetProfileResponseDto;
import com.pio.raonback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}")
  public ResponseEntity<? super GetProfileResponseDto> getProfile(@PathVariable("userId") Long userId) {
    return userService.getProfile(userId);
  }

  @GetMapping("/{userId}/products")
  public ResponseEntity<? super GetProductsResponseDto> getProducts(
      @PathVariable("userId") Long userId,
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "status", direction = Sort.Direction.ASC),
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {
    return userService.getProducts(userId, pageable);
  }

}
