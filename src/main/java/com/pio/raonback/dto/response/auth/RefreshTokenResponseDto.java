package com.pio.raonback.dto.response.auth;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class RefreshTokenResponseDto extends ResponseDto {

  private Data data;

  @Getter
  private static class Data {

    private String accessToken;
    private Long accessTokenExpirationTime;

    private Data(String accessToken, Long accessTokenExpirationTime) {
      this.accessToken = accessToken;
      this.accessTokenExpirationTime = accessTokenExpirationTime;
    }

  }

  private RefreshTokenResponseDto(String accessToken, Long accessTokenExpirationTime) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = new Data(accessToken, accessTokenExpirationTime);
  }

  public static ResponseEntity<RefreshTokenResponseDto> ok(String accessToken, String refreshTokenCookie, Long accessTokenExpirationTime) {
    RefreshTokenResponseDto responseBody = new RefreshTokenResponseDto(accessToken, accessTokenExpirationTime);
    return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, refreshTokenCookie).body(responseBody);
  }

}
