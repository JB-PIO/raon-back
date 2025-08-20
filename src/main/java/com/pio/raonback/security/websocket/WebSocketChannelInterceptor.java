package com.pio.raonback.security.websocket;

import com.pio.raonback.common.ResponseCode;
import com.pio.raonback.security.JwtAuthenticationToken;
import com.pio.raonback.security.exception.ExpiredJwtException;
import com.pio.raonback.security.exception.UsernameCannotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {

  private final AuthenticationManager authenticationManager;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      String token = accessor.getPasscode();
      Authentication authRequest = new JwtAuthenticationToken(token);
      try {
        Authentication authResponse = authenticationManager.authenticate(authRequest);
        accessor.setUser((Principal) authResponse.getPrincipal());
      } catch (ExpiredJwtException exception) {
        throw new MessageDeliveryException(ResponseCode.EXPIRED_TOKEN);
      } catch (UsernameCannotFoundException exception) {
        throw new MessageDeliveryException(ResponseCode.SERVER_ERROR);
      } catch (AuthenticationException exception) {
        throw new MessageDeliveryException(ResponseCode.AUTH_FAILED);
      }
    }
    return message;
  }

}
