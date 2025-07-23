package com.pio.raonback.controller;

import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.chat.GetChatListResponseDto;
import com.pio.raonback.dto.response.chat.GetMessageListResponseDto;
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

  @GetMapping("")
  public ResponseEntity<? super GetChatListResponseDto> getChatList(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<? super GetChatListResponseDto> response = chatService.getChatList(page, size, principal);
    return response;
  }

  @GetMapping("/{chatId}/message")
  public ResponseEntity<? super GetMessageListResponseDto> getMessageList(@PathVariable("chatId") Long chatId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "20") int size,
                                                                          @AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<? super GetMessageListResponseDto> response = chatService.getMessageList(chatId, page, size, principal);
    return response;
  }

  @PostMapping("/{chatId}/message")
  public ResponseEntity<ResponseDto> sendMessage(@PathVariable("chatId") Long chatId,
                                                 @RequestBody @Valid SendMessageRequestDto requestBody,
                                                 @AuthenticationPrincipal RaonUser principal) {
    ResponseEntity<ResponseDto> response = chatService.sendMessage(chatId, requestBody, principal);
    return response;
  }

}
