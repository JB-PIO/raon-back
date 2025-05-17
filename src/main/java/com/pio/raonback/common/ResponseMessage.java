package com.pio.raonback.common;

public interface ResponseMessage {

  // HTTP Status 200
  String SUCCESS = "Success.";

  // HTTP Status 400
  String VALIDATION_FAILED = "Validation failed.";
  String DUPLICATE_EMAIL = "Duplicate email.";
  String DUPLICATE_NICKNAME = "Duplicate nickname.";

  // HTTP Status 401
  String SIGN_IN_FAILED = "Login information is incorrect.";
  String AUTHORIZATION_FAILED = "Authorization failed.";

  // HTTP Status 500
  String DATABASE_ERROR = "Database error.";

}
