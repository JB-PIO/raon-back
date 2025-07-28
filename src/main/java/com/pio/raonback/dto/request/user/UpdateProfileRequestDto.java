package com.pio.raonback.dto.request.user;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class UpdateProfileRequestDto {

  @Size(max = 16, message = "닉네임은 16자 이하로 입력해주세요.")
  private String nickname;

  @URL(message = "올바른 URL 형식이 아닙니다.")
  private String profileImage;

  private Long locationId;

}
