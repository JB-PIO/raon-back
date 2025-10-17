package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetFavoriteStatusResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private Boolean isFavorite;

    private Data(Boolean isFavorite) {
      this.isFavorite = isFavorite;
    }

  }

  private GetFavoriteStatusResponseDto(Boolean isFavorite) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(isFavorite);
  }

  public static ResponseEntity<GetFavoriteStatusResponseDto> ok(Boolean isFavorite) {
    GetFavoriteStatusResponseDto responseBody = new GetFavoriteStatusResponseDto(isFavorite);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
