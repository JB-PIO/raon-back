package com.pio.raonback.controller;

import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.chat.*;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  @GetMapping("")
  public ResponseEntity<? super GetChatListResponseDto> getChats(@PageableDefault(size = 10, sort = "lastMessageAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @AuthenticationPrincipal RaonUser principal) {
    return chatService.getChats(pageable, principal);
  }

  @GetMapping("/{chatId}")
  public ResponseEntity<? super GetChatResponseDto> getChat(@PathVariable("chatId") Long chatId,
                                                            @AuthenticationPrincipal RaonUser principal) {
    return chatService.getChat(chatId, principal);
  }

  @GetMapping("/{chatId}/message")
  public ResponseEntity<? super GetMessageListResponseDto> getMessages(@PathVariable("chatId") Long chatId,
                                                                       @PageableDefault(size = 20, sort = "sentAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                       @AuthenticationPrincipal RaonUser principal) {
    return chatService.getMessages(chatId, pageable, principal);
  }

  @PostMapping("/{chatId}/message")
  public ResponseEntity<? super SendMessageResponseDto> sendMessage(@PathVariable("chatId") Long chatId,
                                                                    @RequestBody @Valid SendMessageRequestDto requestBody,
                                                                    @AuthenticationPrincipal RaonUser principal) {
    return chatService.sendMessage(chatId, requestBody, principal);
  }

  @PostMapping("/{chatId}/fraud-detection")
  public ResponseEntity<? super DetectFraudResponseDto> detectFraud(@PathVariable("chatId") Long chatId,
                                                                    @RequestParam(defaultValue = "50") int size,
                                                                    @AuthenticationPrincipal RaonUser principal) {
    return chatService.detectFraud(chatId, size, principal);
  }

  @PostMapping("/{chatId}/image-analysis")
  public ResponseEntity<? super AnalyzeImagesResponseDto> analyzeImages(@PathVariable("chatId") Long chatId,
                                                                        @AuthenticationPrincipal RaonUser principal) {
    return chatService.analyzeImages(chatId, principal);
  }

  @PutMapping("/{chatId}/read")
  public ResponseEntity<ResponseDto> readMessages(@PathVariable("chatId") Long chatId,
                                                  @AuthenticationPrincipal RaonUser principal) {
    return chatService.readMessages(chatId, principal);
  }

}
