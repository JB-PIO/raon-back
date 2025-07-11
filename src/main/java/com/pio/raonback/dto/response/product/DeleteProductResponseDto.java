package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DeleteProductResponseDto extends ResponseDto {

  private DeleteProductResponseDto() {
    super(ResponseCode.OK, ResponseMessage.OK);
  }

  public static ResponseEntity<DeleteProductResponseDto> ok() {
    DeleteProductResponseDto responseBody = new DeleteProductResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noPermission() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> productNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.PRODUCT_NOT_FOUND, ResponseMessage.PRODUCT_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

}
