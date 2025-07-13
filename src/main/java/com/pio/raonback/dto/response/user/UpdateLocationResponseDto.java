package com.pio.raonback.dto.response.user;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UpdateLocationResponseDto extends ResponseDto {

  private UpdateLocationResponseDto() {
    super(ResponseCode.OK, ResponseMessage.OK);
  }

  public static ResponseEntity<UpdateLocationResponseDto> ok() {
    UpdateLocationResponseDto responseBody = new UpdateLocationResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> locationNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.LOCATION_NOT_FOUND, ResponseMessage.LOCATION_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

}
