package com.pio.raonback.dto.response.auth;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignOutResponseDto extends ResponseDto {

  private SignOutResponseDto() {
    super(ResponseCode.OK, ResponseMessage.OK);
  }

  public static ResponseEntity<SignOutResponseDto> ok() {
    SignOutResponseDto responseBody = new SignOutResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
