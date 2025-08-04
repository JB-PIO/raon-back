package com.pio.raonback.service;

import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.chat.GetChatListResponseDto;
import com.pio.raonback.dto.response.chat.GetChatResponseDto;
import com.pio.raonback.dto.response.chat.GetMessageListResponseDto;
import com.pio.raonback.dto.response.chat.SendMessageResponseDto;
import com.pio.raonback.security.RaonUser;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ChatService {

  ResponseEntity<? super GetChatListResponseDto> getChatList(Pageable pageable, RaonUser principal);

  ResponseEntity<? super GetChatResponseDto> getChat(Long chatId, RaonUser principal);

  ResponseEntity<? super GetMessageListResponseDto> getMessageList(Long chatId, Pageable pageable, RaonUser principal);

  ResponseEntity<? super SendMessageResponseDto> sendMessage(Long chatId, SendMessageRequestDto dto, RaonUser principal);

  ResponseEntity<ResponseDto> readMessages(Long chatId, RaonUser principal);

}
