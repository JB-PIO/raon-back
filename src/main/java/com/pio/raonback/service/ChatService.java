package com.pio.raonback.service;

import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.chat.GetChatListResponseDto;
import com.pio.raonback.dto.response.chat.GetMessageListResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.http.ResponseEntity;

public interface ChatService {

  ResponseEntity<? super GetChatListResponseDto> getChatList(int page, int size, RaonUser user);

  ResponseEntity<? super GetMessageListResponseDto> getMessageList(Long chatId, int page, int size, RaonUser user);

  ResponseEntity<ResponseDto> sendMessage(Long chatId, SendMessageRequestDto dto, RaonUser user);

}
