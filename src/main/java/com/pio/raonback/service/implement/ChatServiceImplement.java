package com.pio.raonback.service.implement;

import com.pio.raonback.dto.external.response.AnalyzeImagesExternalResponseDto;
import com.pio.raonback.dto.external.response.DetectFraudExternalResponseDto;
import com.pio.raonback.dto.object.MessageListItem;
import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.chat.*;
import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.Message;
import com.pio.raonback.entity.ProductImage;
import com.pio.raonback.entity.User;
import com.pio.raonback.repository.ChatRepository;
import com.pio.raonback.repository.MessageRepository;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.AiService;
import com.pio.raonback.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImplement implements ChatService {

  private final AiService aiService;

  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;

  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ResponseEntity<? super GetChatListResponseDto> getChatList(Pageable pageable, RaonUser principal) {
    User user = principal.getUser();

    Page<Chat> chatPage = chatRepository.findAllByBuyerOrSellerAndLastMessageAtNotNull(user, user, pageable);

    Map<Long, Long> unreadCounts = new HashMap<>();
    for (Chat chat : chatPage.getContent()) {
      Long unreadMessageCount = messageRepository.countByChatAndSenderNotAndIsReadFalseAndIsDeletedFalse(chat, user);
      unreadCounts.put(chat.getChatId(), unreadMessageCount);
    }

    return GetChatListResponseDto.ok(chatPage, unreadCounts);
  }

  @Override
  public ResponseEntity<? super GetChatResponseDto> getChat(Long chatId, RaonUser principal) {
    User user = principal.getUser();

    Optional<Chat> optionalChat = chatRepository.findById(chatId);
    if (optionalChat.isEmpty()) return ResponseDto.chatNotFound();
    Chat chat = optionalChat.get();
    if (!chat.getSeller().equals(user) && !chat.getBuyer().equals(user)) return ResponseDto.noPermission();

    return GetChatResponseDto.ok(chat);
  }

  @Override
  public ResponseEntity<? super GetMessageListResponseDto> getMessageList(Long chatId, Pageable pageable, RaonUser principal) {
    User user = principal.getUser();

    Optional<Chat> optionalChat = chatRepository.findById(chatId);
    if (optionalChat.isEmpty()) return ResponseDto.chatNotFound();
    Chat chat = optionalChat.get();
    if (!chat.getSeller().equals(user) && !chat.getBuyer().equals(user)) return ResponseDto.noPermission();

    Page<Message> messagePage = messageRepository.findAllByChatAndIsDeletedFalse(chat, pageable);
    return GetMessageListResponseDto.ok(messagePage);
  }

  @Override
  public ResponseEntity<? super SendMessageResponseDto> sendMessage(Long chatId, SendMessageRequestDto dto, RaonUser principal) {
    User sender = principal.getUser();

    Optional<Chat> optionalChat = chatRepository.findById(chatId);
    if (optionalChat.isEmpty()) return ResponseDto.chatNotFound();
    Chat chat = optionalChat.get();
    if (!chat.getSeller().equals(sender) && !chat.getBuyer().equals(sender)) return ResponseDto.noPermission();

    User receiver = chat.getSeller().equals(sender) ? chat.getBuyer() : chat.getSeller();
    Message message = new Message(chat, sender, dto.getContent(), dto.getImageUrl());
    messageRepository.save(message);
    chat.setLastMessageAt(message.getSentAt());
    chatRepository.save(chat);

    MessageListItem messageListItem = new MessageListItem(message);
    messagingTemplate.convertAndSend("/user/" + receiver.getUserId() + "/chat", messageListItem);

    return SendMessageResponseDto.ok(message);
  }

  @Override
  public ResponseEntity<? super DetectFraudResponseDto> detectFraud(Long chatId, int size, RaonUser principal) {
    User requester = principal.getUser();

    Optional<Chat> optionalChat = chatRepository.findById(chatId);
    if (optionalChat.isEmpty()) return ResponseDto.chatNotFound();
    Chat chat = optionalChat.get();
    if (!chat.getSeller().equals(requester) && !chat.getBuyer().equals(requester)) return ResponseDto.noPermission();

    Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "sentAt"));
    Page<Message> messagePage = messageRepository.findAllByChatAndIsDeletedFalse(chat, pageable);
    List<Message> messages = new ArrayList<>(messagePage.getContent());
    Collections.reverse(messages);

    DetectFraudExternalResponseDto responseBody = aiService.analyzeMessages(requester.getUserId(), messages);

    return DetectFraudResponseDto.ok(responseBody);
  }

  @Override
  public ResponseEntity<? super AnalyzeImagesResponseDto> analyzeImages(Long chatId, RaonUser principal) {
    User buyer = principal.getUser();

    Optional<Chat> optionalChat = chatRepository.findById(chatId);
    if (optionalChat.isEmpty()) return ResponseDto.chatNotFound();
    Chat chat = optionalChat.get();
    if (!chat.getBuyer().equals(buyer)) return ResponseDto.noPermission();

    List<ProductImage> productImages = chat.getProduct().getImages();
    for (ProductImage productImage : productImages) {
      System.out.println(productImage.getImageId() + ": " + productImage.getImageUrl());
    }
    AnalyzeImagesExternalResponseDto responseBody = aiService.analyzeImages(productImages);

    return AnalyzeImagesResponseDto.ok(responseBody);
  }

  @Override
  public ResponseEntity<ResponseDto> readMessages(Long chatId, RaonUser principal) {
    User reader = principal.getUser();

    Optional<Chat> optionalChat = chatRepository.findById(chatId);
    if (optionalChat.isEmpty()) return ResponseDto.chatNotFound();
    Chat chat = optionalChat.get();
    if (!chat.getSeller().equals(reader) && !chat.getBuyer().equals(reader)) return ResponseDto.noPermission();

    List<Message> unreadMessages = messageRepository.findAllByChatAndSenderNotAndIsReadFalseAndIsDeletedFalse(chat, reader);
    for (Message unreadMessage : unreadMessages) unreadMessage.read();
    messageRepository.saveAll(unreadMessages);

    return ResponseDto.ok();
  }

}
