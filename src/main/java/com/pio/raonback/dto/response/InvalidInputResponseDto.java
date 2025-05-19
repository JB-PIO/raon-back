package com.pio.raonback.dto.response;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
public class InvalidInputResponseDto extends ResponseDto {

  private Map<String, String> errors;

  public InvalidInputResponseDto(Map<String, String> errors) {
    super(ResponseCode.INVALID_INPUT, ResponseMessage.INVALID_INPUT);
    this.errors = errors;
  }

  public static ResponseEntity<InvalidInputResponseDto> invalidInput(Map<String, String> errors) {
    InvalidInputResponseDto responseBody = new InvalidInputResponseDto(errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

}
