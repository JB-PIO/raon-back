package com.pio.raonback.dto.request.chat;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class SendMessageRequestDto {

  private String content;

  @URL(message = "올바른 URL 형식이 아닙니다.")
  private String imageUrl;

}
