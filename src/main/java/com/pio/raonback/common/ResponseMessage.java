package com.pio.raonback.common;

public interface ResponseMessage {

  // HTTP Status 200
  String OK = "요청이 성공적으로 처리되었습니다.";

  // HTTP Status 400
  String BAD_REQUEST = "잘못된 요청입니다.";
  String INVALID_INPUT = "입력값이 유효하지 않습니다.";
  String EMAIL_EXISTS = "이미 사용 중인 이메일입니다.";
  String NICKNAME_EXISTS = "이미 사용 중인 닉네임입니다.";
  String LOCATION_NOT_FOUND = "존재하지 않는 지역입니다.";

  // HTTP Status 401
  String SIGN_IN_FAILED = "이메일 또는 비밀번호가 일치하지 않습니다.";
  String AUTH_FAILED = "인증에 실패했습니다.";

  // HTTP Status 500
  String SERVER_ERROR = "서버 오류가 발생했습니다.";

}
