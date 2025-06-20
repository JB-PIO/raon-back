package com.pio.raonback.dto.response.user;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UpdateNicknameResponseDto extends ResponseDto {

  private UpdateNicknameResponseDto() {
    super(ResponseCode.OK, ResponseMessage.OK);
  }

  public static ResponseEntity<UpdateNicknameResponseDto> ok() {
    UpdateNicknameResponseDto responseBody = new UpdateNicknameResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> authFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.AUTH_FAILED, ResponseMessage.AUTH_FAILED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> nicknameExists() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NICKNAME_EXISTS, ResponseMessage.NICKNAME_EXISTS);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
  }

}
