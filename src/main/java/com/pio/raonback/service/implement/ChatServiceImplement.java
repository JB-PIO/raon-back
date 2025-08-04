package com.pio.raonback.service.implement;

import com.pio.raonback.dto.object.MessageListItem;
import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.chat.GetChatListResponseDto;
import com.pio.raonback.dto.response.chat.GetChatResponseDto;
import com.pio.raonback.dto.response.chat.GetMessageListResponseDto;
import com.pio.raonback.dto.response.chat.SendMessageResponseDto;
import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.Message;
import com.pio.raonback.entity.User;
import com.pio.raonback.repository.ChatRepository;
import com.pio.raonback.repository.MessageRepository;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImplement implements ChatService {

  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;

  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ResponseEntity<? super GetChatListResponseDto> getChatList(Pageable pageable, RaonUser principal) {
    User user = principal.getUser();

    Page<Chat> chatPage = chatRepository.findAllByBuyerOrSeller(user, user, pageable);

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
