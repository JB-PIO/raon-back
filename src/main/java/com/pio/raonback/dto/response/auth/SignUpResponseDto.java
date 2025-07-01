package com.pio.raonback.dto.response.auth;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponseDto extends ResponseDto {

  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpirationTime;

  private SignUpResponseDto(String accessToken, String refreshToken, Long accessTokenExpirationTime) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenExpirationTime = accessTokenExpirationTime;
  }

  public static ResponseEntity<SignUpResponseDto> ok(String accessToken, String refreshToken, Long accessTokenExpirationTime) {
    SignUpResponseDto responseBody = new SignUpResponseDto(accessToken, refreshToken, accessTokenExpirationTime);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> locationNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.LOCATION_NOT_FOUND, ResponseMessage.LOCATION_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> emailExists() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.EMAIL_EXISTS, ResponseMessage.EMAIL_EXISTS);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> nicknameExists() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NICKNAME_EXISTS, ResponseMessage.NICKNAME_EXISTS);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
  }

}
