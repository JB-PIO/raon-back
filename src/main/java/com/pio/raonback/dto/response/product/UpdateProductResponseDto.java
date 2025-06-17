package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UpdateProductResponseDto extends ResponseDto {

  private UpdateProductResponseDto() {
    super(ResponseCode.OK, ResponseMessage.OK);
  }

  public static ResponseEntity<UpdateProductResponseDto> ok() {
    UpdateProductResponseDto responseBody = new UpdateProductResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> notLeafCategory() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_LEAF_CATEGORY, ResponseMessage.NOT_LEAF_CATEGORY);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> authFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.AUTH_FAILED, ResponseMessage.AUTH_FAILED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noPermission() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> productNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.PRODUCT_NOT_FOUND, ResponseMessage.PRODUCT_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> locationNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.LOCATION_NOT_FOUND, ResponseMessage.LOCATION_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> categoryNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.CATEGORY_NOT_FOUND, ResponseMessage.CATEGORY_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

}
