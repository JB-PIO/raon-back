package com.pio.raonback.common;

public interface ResponseCode {

  // HTTP Status 200 - OK
  String OK = "OK";

  // HTTP Status 400 - Bad Request
  String BAD_REQUEST = "BAD_REQ";
  String INVALID_INPUT = "INV_INPUT";

  // HTTP Status 401 - Unauthorized
  String SIGN_IN_FAILED = "SIGN_FAIL";
  String AUTH_FAILED = "AUTH_FAIL";
  String EXPIRED_TOKEN = "EXP_TOKEN";

  // HTTP Status 403 - Forbidden
  String NO_PERMISSION = "NO_PER";
  String SUSPENDED_USER = "SUS_ACC";

  // HTTP Status 404 - Not Found
  String NOT_FOUND = "404";
  String PRODUCT_NOT_FOUND = "PRD_404";
  String LOCATION_NOT_FOUND = "LOC_404";
  String CATEGORY_NOT_FOUND = "CAT_404";

  // HTTP Status 406 - Not Acceptable
  String NOT_LEAF_CATEGORY = "NOT_LEAF";

  // Http Status 409 - Conflict
  String EMAIL_EXISTS = "EMAIL_DUP";
  String NICKNAME_EXISTS = "NICK_DUP";

  // HTTP Status 500 - Internal Server Error
  String SERVER_ERROR = "SRV_ERR";

}
