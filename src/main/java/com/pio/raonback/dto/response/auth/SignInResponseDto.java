package com.pio.raonback.dto.response.auth;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignInResponseDto extends ResponseDto {

  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpirationTime;

  private SignInResponseDto(String accessToken, String refreshToken, Long accessTokenExpirationTime) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenExpirationTime = accessTokenExpirationTime;
  }

  public static ResponseEntity<SignInResponseDto> ok(String accessToken, String refreshToken, Long accessTokenExpirationTime) {
    SignInResponseDto responseBody = new SignInResponseDto(accessToken, refreshToken, accessTokenExpirationTime);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> signInFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAILED, ResponseMessage.SIGN_IN_FAILED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> suspendedUser() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SUSPENDED_USER, ResponseMessage.SUSPENDED_USER);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

}
