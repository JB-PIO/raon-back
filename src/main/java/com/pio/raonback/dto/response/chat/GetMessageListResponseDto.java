package com.pio.raonback.dto.response.chat;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.MessageListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.MessageEntity;
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

  private GetMessageListResponseDto(Page<MessageEntity> messageEntitiesPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.messageList = MessageListItem.copyList(messageEntitiesPage);
    this.currentPage = messageEntitiesPage.getNumber();
    this.totalPages = messageEntitiesPage.getTotalPages();
    this.totalElements = messageEntitiesPage.getTotalElements();
  }

  public static ResponseEntity<GetMessageListResponseDto> ok(Page<MessageEntity> messageEntitiesPage) {
    GetMessageListResponseDto responseBody = new GetMessageListResponseDto(messageEntitiesPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
