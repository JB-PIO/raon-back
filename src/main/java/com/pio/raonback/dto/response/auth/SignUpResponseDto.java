package com.pio.raonback.dto.response.auth;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SignUpResponseDto extends ResponseDto {

  private String accessToken;
  private Long accessTokenExpirationTime;

  private SignUpResponseDto(String accessToken, Long accessTokenExpirationTime) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.accessToken = accessToken;
    this.accessTokenExpirationTime = accessTokenExpirationTime;
  }

  public static ResponseEntity<SignUpResponseDto> ok(String accessToken, String refreshTokenCookie, Long accessTokenExpirationTime) {
    SignUpResponseDto responseBody = new SignUpResponseDto(accessToken, accessTokenExpirationTime);
    return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, refreshTokenCookie).body(responseBody);
  }

}
