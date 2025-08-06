package com.pio.raonback.controller;

import com.pio.raonback.dto.request.me.UpdateProfileRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.me.GetFavoriteListResponseDto;
import com.pio.raonback.dto.response.me.GetProfileResponseDto;
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

  @GetMapping("/favorite")
  public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "product.isSold", direction = Sort.Direction.ASC),
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @AuthenticationPrincipal RaonUser principal
  ) {
    return meService.getFavoriteList(pageable, principal);
  }

  @PatchMapping("")
  public ResponseEntity<ResponseDto> updateProfile(@RequestBody @Valid UpdateProfileRequestDto requestBody,
                                                   @AuthenticationPrincipal RaonUser principal) {
    return meService.updateProfile(requestBody, principal);
  }

}
