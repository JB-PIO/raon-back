package com.pio.raonback.dto.response.chat;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.external.response.AnalyzeImagesExternalResponseDto;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class AnalyzeImagesResponseDto extends ResponseDto {

  private AnalyzeImagesExternalResponseDto data;

  private AnalyzeImagesResponseDto(AnalyzeImagesExternalResponseDto dto) {
    super(ResponseCode.OK, ResponseMessage.OK);
    this.data = dto;
  }

  public static ResponseEntity<AnalyzeImagesResponseDto> ok(AnalyzeImagesExternalResponseDto dto) {
    AnalyzeImagesResponseDto responseBody = new AnalyzeImagesResponseDto(dto);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
