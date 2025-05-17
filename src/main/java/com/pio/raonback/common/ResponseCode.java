package com.pio.raonback.common;

public interface ResponseCode {

  // HTTP Status 200
  String SUCCESS = "SU";

  // HTTP Status 400
  String VALIDATION_FAILED = "VF";
  String DUPLICATE_EMAIL = "DE";
  String DUPLICATE_NICKNAME = "DN";

  // HTTP Status 401
  String SIGN_IN_FAILED = "SF";
  String AUTHORIZATION_FAILED = "AF";

  // HTTP Status 500
  String DATABASE_ERROR = "DBE";

}
