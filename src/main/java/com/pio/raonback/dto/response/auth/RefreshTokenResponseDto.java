package com.pio.raonback.dto.response.auth;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class RefreshTokenResponseDto extends ResponseDto {

  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpirationTime;

  private RefreshTokenResponseDto(String accessToken, String refreshToken, Long accessTokenExpirationTime) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenExpirationTime = accessTokenExpirationTime;
  }

  public static ResponseEntity<RefreshTokenResponseDto> ok(String accessToken, String refreshToken, Long accessTokenExpirationTime) {
    RefreshTokenResponseDto responseBody = new RefreshTokenResponseDto(accessToken, refreshToken, accessTokenExpirationTime);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> invalidToken() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

}
