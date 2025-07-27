package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostProductResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private Long productId;

    private Data(Long productId) {
      this.productId = productId;
    }

  }

  private PostProductResponseDto(Long productId) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(productId);
  }

  public static ResponseEntity<PostProductResponseDto> ok(Long productId) {
    PostProductResponseDto responseBody = new PostProductResponseDto(productId);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
