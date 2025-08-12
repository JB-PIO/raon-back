package com.pio.raonback.common;

public interface ResponseMessage {

  // HTTP Status 200 - OK
  String OK = "요청이 성공적으로 처리되었습니다.";

  // HTTP Status 400 - Bad Request
  String BAD_REQUEST = "잘못된 요청입니다.";
  String INVALID_INPUT = "입력값이 유효하지 않습니다.";

  // HTTP Status 401 - Unauthorized
  String SIGN_IN_FAILED = "이메일 또는 비밀번호가 일치하지 않습니다.";
  String AUTH_FAILED = "인증에 실패했습니다.";
  String EXPIRED_TOKEN = "만료된 토큰입니다.";

  // HTTP Status 403 - Forbidden
  String NO_PERMISSION = "권한이 없습니다.";
  String SUSPENDED_USER = "정지된 계정입니다.";
  String OWN_PRODUCT = "본인의 상품입니다.";
  String SOLD_PRODUCT = "판매 완료된 상품입니다.";

  // HTTP Status 404 - Not Found
  String NOT_FOUND = "존재하지 않는 리소스입니다.";
  String PRODUCT_NOT_FOUND = "존재하지 않는 상품입니다.";
  String LOCATION_NOT_FOUND = "존재하지 않는 지역입니다.";
  String CATEGORY_NOT_FOUND = "존재하지 않는 카테고리입니다.";
  String CHAT_NOT_FOUND = "존재하지 않는 채팅방입니다.";
  String USER_NOT_FOUND = "존재하지 않는 유저입니다.";

  // HTTP Status 406 - Not Acceptable
  String NOT_LEAF_CATEGORY = "최하위 카테고리를 선택해주세요.";
  String NOT_BUYER = "구매 희망자가 아닙니다.";

  // HTTP Status 409 - Conflict
  String EMAIL_EXISTS = "이미 사용 중인 이메일입니다.";
  String NICKNAME_EXISTS = "이미 사용 중인 닉네임입니다.";
  String CHAT_EXISTS = "이미 존재하는 채팅방이 있습니다.";

  // HTTP Status 500 - Internal Server Error
  String SERVER_ERROR = "서버 오류가 발생했습니다.";

}
