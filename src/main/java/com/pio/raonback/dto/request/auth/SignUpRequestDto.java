package com.pio.raonback.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

  @NotBlank(message = "이메일을 입력해주세요.")
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Size(min = 8, max = 20, message = "비밀번호는 8~20자로 입력해주세요.")
  private String password;

  @NotBlank(message = "닉네임을 입력해주세요.")
  @Size(max = 16, message = "닉네임은 16자 이하로 입력해주세요.")
  private String nickname;

  @NotNull(message = "지역을 선택해주세요.")
  private Long locationId;

}
