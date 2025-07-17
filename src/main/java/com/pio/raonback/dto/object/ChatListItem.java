package com.pio.raonback.dto.object;

import com.pio.raonback.entity.ChatEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
  private String createdAt;
  private String lastMessageAt;

  public ChatListItem(ChatEntity chatEntity) {
    this.chatId = chatEntity.getChatId();
    this.productId = chatEntity.getProductId();
    this.buyerId = chatEntity.getBuyerId();
    this.sellerId = chatEntity.getSellerId();
    this.createdAt = chatEntity.getCreatedAt();
    this.lastMessageAt = chatEntity.getLastMessageAt();
  }

  public static List<ChatListItem> copyList(Page<ChatEntity> chatEntitiesPage) {
    List<ChatListItem> list = new ArrayList<>();
    for (ChatEntity chatEntity : chatEntitiesPage.getContent()) {
      ChatListItem chatListItem = new ChatListItem(chatEntity);
      list.add(chatListItem);
    }
    return list;
  }

}
