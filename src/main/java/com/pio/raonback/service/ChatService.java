package com.pio.raonback.service;

import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.http.ResponseEntity;

public interface ChatService {

  ResponseEntity<ResponseDto> sendMessage(Long chatId, SendMessageRequestDto dto, RaonUser user);

}
