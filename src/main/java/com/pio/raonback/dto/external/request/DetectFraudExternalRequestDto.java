package com.pio.raonback.dto.external.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Message;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DetectFraudExternalRequestDto {

  private Long requesterId;
  private List<MessageListItem> messages;

  @Getter
  private static class MessageListItem {

    private Long messageId;
    private Long senderId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentAt;

    private MessageListItem(Message message) {
      this.messageId = message.getMessageId();
      this.senderId = message.getSender().getUserId();
      this.content = message.getContent();
      this.sentAt = message.getSentAt();
    }

    private static List<MessageListItem> fromMessages(List<Message> messages) {
      List<MessageListItem> list = new ArrayList<>();
      for (Message message : messages) {
        MessageListItem messageListItem = new MessageListItem(message);
        list.add(messageListItem);
      }
      return list;
    }

  }

  public DetectFraudExternalRequestDto(Long requesterId, List<Message> messages) {
    this.requesterId = requesterId;
    this.messages = MessageListItem.fromMessages(messages);
  }

}
