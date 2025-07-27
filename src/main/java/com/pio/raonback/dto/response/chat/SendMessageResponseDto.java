package com.pio.raonback.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Message;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class SendMessageResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private Long messageId;
    private Long chatId;
    private UserData sender;
    private String content;
    private String imageUrl;
    private Boolean isRead;
    private Boolean isDeleted;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentAt;

    @Getter
    private static class UserData {

      private Long userId;
      private String nickname;
      private String profileImage;
      private Boolean isDeleted;

      private UserData(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.isDeleted = user.getIsDeleted();
      }

    }

    private Data(Message message) {
      this.messageId = message.getMessageId();
      this.chatId = message.getChat().getChatId();
      this.sender = new UserData(message.getSender());
      this.content = message.getContent();
      this.imageUrl = message.getImageUrl();
      this.isRead = message.getIsRead();
      this.isDeleted = message.getIsDeleted();
      this.sentAt = message.getSentAt();
    }

  }

  private SendMessageResponseDto(Message message) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(message);
  }

  public static ResponseEntity<SendMessageResponseDto> ok(Message message) {
    SendMessageResponseDto responseBody = new SendMessageResponseDto(message);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
