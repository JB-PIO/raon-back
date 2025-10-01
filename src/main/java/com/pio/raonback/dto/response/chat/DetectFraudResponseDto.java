package com.pio.raonback.dto.response.chat;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.external.response.DetectFraudExternalResponseDto;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DetectFraudResponseDto extends ResponseDto {

  private DetectFraudExternalResponseDto data;

  private DetectFraudResponseDto(DetectFraudExternalResponseDto dto) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = dto;
  }

  public static ResponseEntity<DetectFraudResponseDto> ok(DetectFraudExternalResponseDto dto) {
    DetectFraudResponseDto responseBody = new DetectFraudResponseDto(dto);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
