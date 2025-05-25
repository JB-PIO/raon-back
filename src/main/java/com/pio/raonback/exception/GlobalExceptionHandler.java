package com.pio.raonback.exception;

import com.pio.raonback.dto.response.InvalidInputResponseDto;
import com.pio.raonback.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<InvalidInputResponseDto> validationExceptionHandler(MethodArgumentNotValidException exception) {
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    Map<String, String> errors = new HashMap<>();
    fieldErrors.forEach(fieldError -> {
      String field = fieldError.getField();
      String message = fieldError.getDefaultMessage();
      errors.put(field, message);
    });
    return InvalidInputResponseDto.invalidInput(errors);
  }

  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
  public ResponseEntity<ResponseDto> badRequestExceptionHandler(Exception exception) {
    return ResponseDto.badRequest();
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ResponseDto> exceptionHandler(Exception exception) {
    return ResponseDto.serverError();
  }

}
