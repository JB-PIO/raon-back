package com.pio.raonback.dto.request.me;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNicknameRequestDto {

  @NotBlank(message = "닉네임을 입력해주세요.")
  @Size(max = 16, message = "닉네임은 16자 이하로 입력해주세요.")
  private String nickname;

}
