package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime sentAt;

  public MessageListItem(Message message) {
    this.messageId = message.getMessageId();
    this.chatId = message.getChat().getChatId();
    this.senderId = message.getSender().getUserId();
    this.content = message.getContent();
    this.imageUrl = message.getImageUrl();
    this.isRead = message.getIsRead();
    this.sentAt = message.getSentAt();
  }

  public static List<MessageListItem> copyList(Page<Message> messagePage) {
    List<MessageListItem> list = new ArrayList<>();
    for (Message message : messagePage.getContent()) {
      MessageListItem messageListItem = new MessageListItem(message);
      list.add(messageListItem);
    }
    return list;
  }

}
