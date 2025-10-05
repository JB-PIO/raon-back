package com.pio.raonback.dto.response.chat;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.ChatListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Chat;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Getter
public class GetChatsResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<ChatListItem> chats;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private Data(Page<Chat> chatPage, Map<Long, Long> unreadCounts) {
      this.chats = ChatListItem.fromChatPage(chatPage, unreadCounts);
      this.currentPage = chatPage.getNumber();
      this.totalPages = chatPage.getTotalPages();
      this.totalElements = chatPage.getTotalElements();
    }

  }

  private GetChatsResponseDto(Page<Chat> chatPage, Map<Long, Long> unreadCounts) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(chatPage, unreadCounts);
  }

  public static ResponseEntity<GetChatsResponseDto> ok(Page<Chat> chatPage, Map<Long, Long> unreadCounts) {
    GetChatsResponseDto responseBody = new GetChatsResponseDto(chatPage, unreadCounts);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
