package com.pio.raonback.common;

public interface ResponseCode {

  // HTTP Status 200
  String OK = "OK";

  // HTTP Status 400
  String BAD_REQUEST = "BAD_REQ";
  String INVALID_INPUT = "INV_INPUT";
  String EMAIL_EXISTS = "EMAIL_DUP";
  String NICKNAME_EXISTS = "NICK_DUP";
  String NOT_LEAF_CATEGORY = "NOT_LEAF";

  // HTTP Status 401
  String SIGN_IN_FAILED = "SIGN_FAIL";
  String AUTH_FAILED = "AUTH_FAIL";

  // HTTP Status 404
  String LOCATION_NOT_FOUND = "LOC_404";
  String CATEGORY_NOT_FOUND = "CAT_404";
  
  // HTTP Status 500
  String SERVER_ERROR = "SRV_ERR";

}
