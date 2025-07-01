package com.pio.raonback.dto.response.product;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostProductResponseDto extends ResponseDto {

  private PostProductResponseDto() {
    super(ResponseCode.OK, ResponseMessage.OK);
  }

  public static ResponseEntity<PostProductResponseDto> ok() {
    PostProductResponseDto responseBody = new PostProductResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> notLeafCategory() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_LEAF_CATEGORY, ResponseMessage.NOT_LEAF_CATEGORY);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
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
