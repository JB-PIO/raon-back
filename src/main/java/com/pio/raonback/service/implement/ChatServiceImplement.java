package com.pio.raonback.service.implement;

import com.pio.raonback.dto.object.MessageListItem;
import com.pio.raonback.dto.request.chat.SendMessageRequestDto;
import com.pio.raonback.dto.response.ResponseDto;
import com.pio.raonback.dto.response.chat.GetChatListResponseDto;
import com.pio.raonback.entity.ChatEntity;
import com.pio.raonback.entity.MessageEntity;
import com.pio.raonback.entity.UserEntity;
import com.pio.raonback.repository.ChatRepository;
import com.pio.raonback.repository.MessageRepository;
import com.pio.raonback.security.RaonUser;
import com.pio.raonback.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImplement implements ChatService {

  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;

  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public ResponseEntity<? super GetChatListResponseDto> getChatList(int page, int size, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    Long userId = userEntity.getUserId();

    Pageable pageable = PageRequest.of(page, size);
    Page<ChatEntity> chatEntitiesPage = chatRepository.findAllByBuyerIdOrSellerIdOrderByLastMessageAtDesc(userId, userId, pageable);
    return GetChatListResponseDto.ok(chatEntitiesPage);
  }

  @Override
  public ResponseEntity<ResponseDto> sendMessage(Long chatId, SendMessageRequestDto dto, RaonUser user) {
    UserEntity userEntity = user.getUserEntity();
    Long senderId = userEntity.getUserId();

    Optional<ChatEntity> optionalChatEntity = chatRepository.findById(chatId);
    if (optionalChatEntity.isEmpty()) return ResponseDto.chatNotFound();
    ChatEntity chatEntity = optionalChatEntity.get();
    if (!chatEntity.getBuyerId().equals(senderId) && !chatEntity.getSellerId().equals(senderId)) {
      return ResponseDto.noPermission();
    }
    Long receiverId = chatEntity.getBuyerId().equals(senderId) ? chatEntity.getSellerId() : chatEntity.getBuyerId();

    MessageEntity messageEntity = new MessageEntity(chatId, dto, senderId);
    messageRepository.save(messageEntity);
    chatEntity.setLastMessageAt(messageEntity.getSentAt());
    chatRepository.save(chatEntity);

    MessageListItem message = new MessageListItem(messageEntity);
    messagingTemplate.convertAndSend("/user/" + receiverId + "/chat", message);

    return ResponseDto.ok();
  }

}
