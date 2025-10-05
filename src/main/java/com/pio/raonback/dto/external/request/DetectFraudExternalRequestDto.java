package com.pio.raonback.dto.external.request;

import com.pio.raonback.dto.object.SimpleMessageListItem;
import com.pio.raonback.entity.Message;
import lombok.Getter;

import java.util.List;

@Getter
public class DetectFraudExternalRequestDto {

  private Long requesterId;
  private List<SimpleMessageListItem> messages;

  public DetectFraudExternalRequestDto(Long requesterId, List<Message> messages) {
    this.requesterId = requesterId;
    this.messages = SimpleMessageListItem.fromMessages(messages);
  }

}
