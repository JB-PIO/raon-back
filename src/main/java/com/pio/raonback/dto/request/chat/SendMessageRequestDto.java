package com.pio.raonback.dto.request.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
public class SendMessageRequestDto {

  private String content;

  @URL(message = "올바른 URL 형식이 아닙니다.")
  private String imageUrl;

}
