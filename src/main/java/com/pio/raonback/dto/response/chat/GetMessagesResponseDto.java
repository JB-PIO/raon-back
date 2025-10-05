package com.pio.raonback.dto.response.chat;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.MessageListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Message;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetMessagesResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<MessageListItem> messages;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private Data(Page<Message> messagePage) {
      this.messages = MessageListItem.fromMessagePage(messagePage);
      this.currentPage = messagePage.getNumber();
      this.totalPages = messagePage.getTotalPages();
      this.totalElements = messagePage.getTotalElements();
    }

  }

  private GetMessagesResponseDto(Page<Message> messagePage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(messagePage);
  }

  public static ResponseEntity<GetMessagesResponseDto> ok(Page<Message> messagePage) {
    GetMessagesResponseDto responseBody = new GetMessagesResponseDto(messagePage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
