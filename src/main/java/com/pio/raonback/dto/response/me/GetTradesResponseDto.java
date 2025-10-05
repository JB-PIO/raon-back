package com.pio.raonback.dto.response.me;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.object.TradeListItem;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.entity.Trade;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetTradesResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private List<TradeListItem> trades;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private Data(Page<Trade> tradePage) {
      this.trades = TradeListItem.fromTradePage(tradePage);
      this.currentPage = tradePage.getNumber();
      this.totalPages = tradePage.getTotalPages();
      this.totalElements = tradePage.getTotalElements();
    }

  }

  private GetTradesResponseDto(Page<Trade> tradePage) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(tradePage);
  }

  public static ResponseEntity<GetTradesResponseDto> ok(Page<Trade> tradePage) {
    GetTradesResponseDto responseBody = new GetTradesResponseDto(tradePage);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
