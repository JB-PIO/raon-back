package com.pio.raonback.dto.object;

import com.pio.raonback.entity.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageListItem {

  private Long messageId;
  private Long chatId;
  private Long senderId;
  private String content;
  private String imageUrl;
  private Boolean isRead;
  private String sentAt;

  public MessageListItem(MessageEntity messageEntity) {
    this.messageId = messageEntity.getMessageId();
    this.chatId = messageEntity.getChatId();
    this.senderId = messageEntity.getSenderId();
    this.content = messageEntity.getContent();
    this.imageUrl = messageEntity.getImageUrl();
    this.isRead = messageEntity.getIsRead();
    this.sentAt = messageEntity.getSentAt();
  }

}
