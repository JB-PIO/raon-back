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

  public static ResponseEntity<ResponseDto> ok() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.OK, ResponseMessage.OK);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> badRequest() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> signInFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAILED, ResponseMessage.SIGN_IN_FAILED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> authFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.AUTH_FAILED, ResponseMessage.AUTH_FAILED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> expiredToken() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.EXPIRED_TOKEN, ResponseMessage.EXPIRED_TOKEN);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noPermission() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> suspendedUser() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SUSPENDED_USER, ResponseMessage.SUSPENDED_USER);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> ownProduct() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.OWN_PRODUCT, ResponseMessage.OWN_PRODUCT);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> soldProduct() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SOLD_PRODUCT, ResponseMessage.SOLD_PRODUCT);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> notFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_FOUND, ResponseMessage.NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> productNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.PRODUCT_NOT_FOUND, ResponseMessage.PRODUCT_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> locationNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.LOCATION_NOT_FOUND, ResponseMessage.LOCATION_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> categoryNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.CATEGORY_NOT_FOUND, ResponseMessage.CATEGORY_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> chatNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.CHAT_NOT_FOUND, ResponseMessage.CHAT_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> userNotFound() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> notLeafCategory() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_LEAF_CATEGORY, ResponseMessage.NOT_LEAF_CATEGORY);
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> emailExists() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.EMAIL_EXISTS, ResponseMessage.EMAIL_EXISTS);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> nicknameExists() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NICKNAME_EXISTS, ResponseMessage.NICKNAME_EXISTS);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> chatExists() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.CHAT_EXISTS, ResponseMessage.CHAT_EXISTS);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> serverError() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SERVER_ERROR, ResponseMessage.SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
  }

}
