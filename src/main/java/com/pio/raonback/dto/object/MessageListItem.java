package com.pio.raonback.dto.object;

import com.pio.raonback.entity.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

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

  public static List<MessageListItem> copyList(Page<MessageEntity> messageEntitiesPage) {
    List<MessageListItem> list = new ArrayList<>();
    for (MessageEntity messageEntity : messageEntitiesPage.getContent()) {
      MessageListItem messageListItem = new MessageListItem(messageEntity);
      list.add(messageListItem);
    }
    return list;
  }

}
