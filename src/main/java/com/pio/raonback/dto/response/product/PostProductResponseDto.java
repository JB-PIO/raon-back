package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostProductResponseDto extends ResponseDto {

  Long productId;

  private PostProductResponseDto(Long productId) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.productId = productId;
  }

  public static ResponseEntity<PostProductResponseDto> ok(Long productId) {
    PostProductResponseDto responseBody = new PostProductResponseDto(productId);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
