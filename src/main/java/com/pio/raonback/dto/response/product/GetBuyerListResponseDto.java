package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.UserListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Chat;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetBuyerListResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<UserListItem> users;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private Data(Page<Chat> chatPage) {
      this.users = UserListItem.fromChatPage(chatPage);
      this.currentPage = chatPage.getNumber();
      this.totalPages = chatPage.getTotalPages();
      this.totalElements = chatPage.getTotalElements();
    }

  }

  private GetBuyerListResponseDto(Page<Chat> chatPage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(chatPage);
  }

  public static ResponseEntity<GetBuyerListResponseDto> ok(Page<Chat> chatPage) {
    GetBuyerListResponseDto responseBody = new GetBuyerListResponseDto(chatPage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
