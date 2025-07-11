package com.pio.raonback.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message"})
public class ResponseDto {

  private String code;
  private String message;

  public static ResponseEntity<ResponseDto> badRequest() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> authFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.AUTH_FAILED, ResponseMessage.AUTH_FAILED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> notFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_FOUND, ResponseMessage.NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> serverError() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SERVER_ERROR, ResponseMessage.SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
  }

}
