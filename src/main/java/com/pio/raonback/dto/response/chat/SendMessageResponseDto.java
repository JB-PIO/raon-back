package com.pio.raonback.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Message;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class SendMessageResponseDto extends ResponseDto {

  private Long messageId;
  private Long chatId;
  private Long senderId;
  private String content;
  private String imageUrl;
  private Boolean isRead;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime sentAt;

  private SendMessageResponseDto(Message message) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.messageId = message.getMessageId();
    this.chatId = message.getChat().getChatId();
    this.senderId = message.getSender().getUserId();
    this.content = message.getContent();
    this.imageUrl = message.getImageUrl();
    this.isRead = message.getIsRead();
    this.sentAt = message.getSentAt();
  }

  public static ResponseEntity<SendMessageResponseDto> ok(Message message) {
    SendMessageResponseDto responseBody = new SendMessageResponseDto(message);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
