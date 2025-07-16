package com.pio.raonback.controller;

import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  @PostMapping("/{chatId}/message")
  public ResponseEntity<ResponseDto> sendMessage(@PathVariable("chatId") Long chatId,
                                                 @RequestBody @Valid SendMessageRequestDto requestBody,
                                                 @AuthenticationPrincipal RaonUser user) {
    ResponseEntity<ResponseDto> response = chatService.sendMessage(chatId, requestBody, user);
    return response;
  }

}
