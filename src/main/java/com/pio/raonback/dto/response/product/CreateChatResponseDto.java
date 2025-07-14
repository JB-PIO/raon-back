package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CreateChatResponseDto extends ResponseDto {

  Long chatId;

  private CreateChatResponseDto(Long chatId) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.chatId = chatId;
  }

  public static ResponseEntity<CreateChatResponseDto> ok(Long chatId) {
    CreateChatResponseDto responseBody = new CreateChatResponseDto(chatId);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
