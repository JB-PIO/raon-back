package com.pio.raonback.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.common.ResponseMessage;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.security.exception.ExpiredJwtException;
import com.pio.raonback.security.exception.UsernameCannotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    ResponseDto responseBody;
    if (exception instanceof ExpiredJwtException) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      responseBody = new ResponseDto(ResponseCode.EXPIRED_TOKEN, ResponseMessage.EXPIRED_TOKEN);
    } else if (exception instanceof UsernameCannotFoundException) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      responseBody = new ResponseDto(ResponseCode.SERVER_ERROR, ResponseMessage.SERVER_ERROR);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      responseBody = new ResponseDto(ResponseCode.AUTH_FAILED, ResponseMessage.AUTH_FAILED);
    }
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonResponse = objectMapper.writeValueAsString(responseBody);
    response.getWriter().write(jsonResponse);
  }

}
