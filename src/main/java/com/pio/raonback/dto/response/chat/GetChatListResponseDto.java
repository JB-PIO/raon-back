package com.pio.raonback.dto.response.chat;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.ChatListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.ChatEntity;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetChatListResponseDto extends ResponseDto {

  private List<ChatListItem> chatList;
  private int currentPage;
  private int totalPages;
  private long totalElements;

  private GetChatListResponseDto(Page<ChatEntity> chatEntitiesPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.chatList = ChatListItem.copyList(chatEntitiesPage);
    this.currentPage = chatEntitiesPage.getNumber();
    this.totalPages = chatEntitiesPage.getTotalPages();
    this.totalElements = chatEntitiesPage.getTotalElements();
  }

  public static ResponseEntity<GetChatListResponseDto> ok(Page<ChatEntity> chatEntitiesPage) {
    GetChatListResponseDto responseBody = new GetChatListResponseDto(chatEntitiesPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
