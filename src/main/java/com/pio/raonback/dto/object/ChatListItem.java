package com.pio.raonback.dto.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pio.raonback.entity.Chat;
import com.pio.raonback.entity.Message;
import com.pio.raonback.entity.Product;
import com.pio.raonback.entity.User;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ChatListItem {

  private Long chatId;
  private ProductData product;
  private UserData buyer;
  private UserData seller;
  private MessageData lastMessage;
  private Long unreadCount;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @Getter
  private static class ProductData {

    private Long productId;
    private String thumbnail;
    private String title;
    private Boolean isSold;
    private Boolean isActive;
    private Boolean isDeleted;

    private ProductData(Product product) {
      this.productId = product.getProductId();
      this.thumbnail = product.getImages().get(0).getImageUrl();
      this.title = product.getTitle();
      this.isSold = product.getIsSold();
      this.isActive = product.getIsActive();
      this.isDeleted = product.getIsDeleted();
    }

  }

  @Getter
  private static class UserData {

    private Long userId;
    private String nickname;
    private String profileImage;
    private Boolean isDeleted;

    private UserData(User user) {
      this.userId = user.getUserId();
      this.nickname = user.getNickname();
      this.profileImage = user.getProfileImage();
      this.isDeleted = user.getIsDeleted();
    }

  }

  @Getter
  private static class MessageData {

    private Long messageId;
    private String content;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentAt;

    private MessageData(Message message) {
      this.messageId = message.getMessageId();
      this.content = message.getContent();
      this.imageUrl = message.getImageUrl();
      this.sentAt = message.getSentAt();
    }

  }

  public ChatListItem(Chat chat, Long unreadCount) {
    this.chatId = chat.getChatId();
    this.product = new ProductData(chat.getProduct());
    this.buyer = new UserData(chat.getBuyer());
    this.seller = new UserData(chat.getSeller());
    this.lastMessage = new MessageData(chat.getMessages().get(chat.getMessages().size() - 1));
    this.unreadCount = unreadCount;
    this.createdAt = chat.getCreatedAt();
  }

  public static List<ChatListItem> copyList(Page<Chat> chatPage, Map<Long, Long> unreadCounts) {
    List<ChatListItem> list = new ArrayList<>();
    for (Chat chat : chatPage.getContent()) {
      ChatListItem chatListItem = new ChatListItem(chat, unreadCounts.get(chat.getChatId()));
      list.add(chatListItem);
    }
    return list;
  }

}
