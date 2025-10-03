package com.pio.raonback.dto.request.me;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class UpdateProfileImageRequestDto {

  @NotBlank(message = "이미지 주소를 입력해주세요.")
  @URL(message = "올바른 URL 형식이 아닙니다.")
  private String profileImage;

}
