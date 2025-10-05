package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Message;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SimpleMessageListItem {

  private Long messageId;
  private Long senderId;
  private String content;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ZonedDateTime sentAt;

  public SimpleMessageListItem(Message message) {
    this.messageId = message.getMessageId();
    this.senderId = message.getSender().getUserId();
    this.content = message.getContent();
    this.sentAt = message.getSentAt();
  }

  public static List<SimpleMessageListItem> fromMessages(List<Message> messages) {
    List<SimpleMessageListItem> list = new ArrayList<>();
    for (Message message : messages) {
      SimpleMessageListItem messageListItem = new SimpleMessageListItem(message);
      list.add(messageListItem);
    }
    return list;
  }

}
