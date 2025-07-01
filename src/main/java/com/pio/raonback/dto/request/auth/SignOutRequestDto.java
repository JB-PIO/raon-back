package com.pio.raonback.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignOutRequestDto {

  @NotBlank(message = "리프레시 토큰을 입력해주세요.")
  private String refreshToken;

}
