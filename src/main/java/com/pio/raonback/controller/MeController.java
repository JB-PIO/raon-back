package com.pio.raonback.controller;

import com.pio.raonback.dto.request.me.UpdateLocationRequestDto;
import com.pio.raonback.dto.request.me.UpdateNicknameRequestDto;
import com.pio.raonback.dto.request.me.UpdateProfileImageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.me.GetFavoritesResponseDto;
import com.pio.raonback.dto.response.me.GetProductsResponseDto;
import com.pio.raonback.dto.response.me.GetProfileResponseDto;
import com.pio.raonback.dto.response.me.GetTradesResponseDto;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.MeService;
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
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MeController {

  private final MeService meService;

  @GetMapping("")
  public ResponseEntity<? super GetProfileResponseDto> getProfile(@AuthenticationPrincipal RaonUser principal) {
    return meService.getProfile(principal);
  }

  @GetMapping("/products")
  public ResponseEntity<? super GetProductsResponseDto> getProducts(
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "status", direction = Sort.Direction.ASC),
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @AuthenticationPrincipal RaonUser principal
  ) {
    return meService.getProducts(pageable, principal);
  }

  @GetMapping("/favorites")
  public ResponseEntity<? super GetFavoritesResponseDto> getFavorites(
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "product.status", direction = Sort.Direction.ASC),
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @AuthenticationPrincipal RaonUser principal
  ) {
    return meService.getFavorites(pageable, principal);
  }

  @GetMapping("/trades")
  public ResponseEntity<? super GetTradesResponseDto> getTrades(
      @RequestParam(defaultValue = "both") String type,
      @PageableDefault(size = 20, sort = "tradedAt", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal RaonUser principal
  ) {
    return meService.getTrades(type, pageable, principal);
  }

  @PatchMapping("/nickname")
  public ResponseEntity<ResponseDto> updateNickname(@RequestBody @Valid UpdateNicknameRequestDto requestBody,
                                                    @AuthenticationPrincipal RaonUser principal) {
    return meService.updateNickname(requestBody, principal);
  }

  @PatchMapping("/profile-image")
  public ResponseEntity<ResponseDto> updateProfileImage(@RequestBody @Valid UpdateProfileImageRequestDto requestBody,
                                                        @AuthenticationPrincipal RaonUser principal) {
    return meService.updateProfileImage(requestBody, principal);
  }

  @PatchMapping("/location")
  public ResponseEntity<ResponseDto> updateLocation(@RequestBody @Valid UpdateLocationRequestDto requestBody,
                                                    @AuthenticationPrincipal RaonUser principal) {
    return meService.updateLocation(requestBody, principal);
  }

  @DeleteMapping("")
  public ResponseEntity<ResponseDto> deleteAccount(@AuthenticationPrincipal RaonUser principal) {
    return meService.deleteAccount(principal);
  }

  @DeleteMapping("/profile-image")
  public ResponseEntity<ResponseDto> deleteProfileImage(@AuthenticationPrincipal RaonUser principal) {
    return meService.deleteProfileImage(principal);
  }

}
