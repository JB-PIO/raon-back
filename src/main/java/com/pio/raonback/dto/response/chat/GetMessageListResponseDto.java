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
public class GetMessageListResponseDto extends ResponseDto {

  private List<MessageListItem> messageList;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  private GetMessageListResponseDto(Page<Message> messagePage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.messageList = MessageListItem.copyList(messagePage);
    this.currentPage = messagePage.getNumber();
    this.totalPages = messagePage.getTotalPages();
    this.totalElements = messagePage.getTotalElements();
  }

  public static ResponseEntity<GetMessageListResponseDto> ok(Page<Message> messagePage) {
    GetMessageListResponseDto responseBody = new GetMessageListResponseDto(messagePage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
