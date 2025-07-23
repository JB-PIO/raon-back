package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatListItem {

  private Long chatId;
  private Long productId;
  private Long buyerId;
  private Long sellerId;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime lastMessageAt;

  public ChatListItem(Chat chat) {
    this.chatId = chat.getChatId();
    this.productId = chat.getProduct().getProductId();
    this.buyerId = chat.getBuyer().getUserId();
    this.sellerId = chat.getSeller().getUserId();
    this.createdAt = chat.getCreatedAt();
    this.lastMessageAt = chat.getLastMessageAt();
  }

  public static List<ChatListItem> copyList(Page<Chat> chatPage) {
    List<ChatListItem> list = new ArrayList<>();
    for (Chat chat : chatPage.getContent()) {
      ChatListItem chatListItem = new ChatListItem(chat);
      list.add(chatListItem);
    }
    return list;
  }

}
