package com.pio.raonback.dto.request.user;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class UpdateProfileImageRequestDto {

  @URL(message = "올바른 URL 형식이 아닙니다.")
  private String profileImage;

}
